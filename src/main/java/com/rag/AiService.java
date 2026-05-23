package com.rag;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final OllamaChatModel model;

    public AiService() {

        this.model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("tinyllama")
                .build();
    }

    public String ask(String question) {
        return model.generate(question);
    }
}
