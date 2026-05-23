package com.rag;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.ollama.*;
import dev.langchain4j.store.embedding.*;
import dev.langchain4j.store.embedding.inmemory.*;
import dev.langchain4j.data.embedding.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class RagService {

    private final OllamaChatModel chatModel;

    private final OllamaEmbeddingModel embeddingModel;

    private final EmbeddingStore<TextSegment> embeddingStore;

    public RagService() {

        chatModel = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("tinyllama")
                .build();

        embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text")
                .build();

        embeddingStore = new InMemoryEmbeddingStore<>();
    }

    public String askFromLLM(String question) {
        return chatModel.generate(question);
    }

    public void processPdf(MultipartFile file) throws Exception {

        PDDocument document =
                PDDocument.load(file.getInputStream());

        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(document);

        document.close();

        List<String> chunks = splitText(text);

        for (String chunk : chunks) {

            TextSegment segment =
                    TextSegment.from(chunk);

            Embedding embedding =
                    embeddingModel.embed(chunk).content();

            embeddingStore.add(embedding, segment);
        }
    }

    private List<String> splitText(String text) {

        List<String> chunks = new ArrayList<>();

        int chunkSize = 1000;

        for (int i = 0; i < text.length(); i += chunkSize) {

            chunks.add(
                    text.substring(
                            i,
                            Math.min(text.length(), i + chunkSize)
                    )
            );
        }

        return chunks;
    }

    public String askFromPDF(String question) {

        Embedding questionEmbedding =
                embeddingModel.embed(question).content();

        EmbeddingSearchRequest request =
                EmbeddingSearchRequest.builder()
                        .queryEmbedding(questionEmbedding)
                        .maxResults(3)
                        .build();

        EmbeddingSearchResult<TextSegment> result =
                embeddingStore.search(request);

        StringBuilder context = new StringBuilder();

        for (EmbeddingMatch<TextSegment> match :
                result.matches()) {

            context.append(match.embedded().text())
                    .append("\n");
        }

        String prompt = """
            Answer based only on below context:

            %s

            Question:
            %s
            """.formatted(context, question);

        return chatModel.generate(prompt);
    }
}
