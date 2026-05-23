What is RAG?

        RAG stands for: Retrieval-Augmented Generation
        Instead of answering only from pre-trained knowledge, the system:
        Retrieves relevant document content
        Sends that content to the LLM
        Generates a grounded answer

This project is a simple local RAG (Retrieval-Augmented Generation) application built using:
* Spring Boot
* LangChain4j
* Ollama
* Apache PDFBox
* Swagger UI

The application allows users to:
* Upload PDF documents
* Extract text from PDFs
* Generate embeddings
* Store embeddings in memory
* Perform similarity search


Architecture:

PDF Upload
↓
Text Extraction
↓
Chunking
↓
Embeddings
↓
Vector Store
↓
Question Embedding
↓
Similarity Search
↓
Relevant Chunks
↓
LLM (Ollama)
↓
Final Answer

| Component       | Technology                |
| --------------- | ------------------------- |
| Backend         | Spring Boot               |
| AI Framework    | LangChain4j               |
| LLM             | Ollama                    |
| Embedding Model | nomic-embed-text          |
| Chat Model      | tinyllama / mistral       |
| PDF Parsing     | Apache PDFBox             |
| API Testing     | Swagger UI                |
| Vector Store    | In-memory embedding store |




Install Ollama
Download" https://ollama.com/download
Verify installation:
ollama --version


Pull Required Models
Chat Model: **ollama pull tinyllama**

Embedding model: **ollama pull nomic-embed-text**

Start Ollama app, verify: http://localhost:11434

Upload PDF: http://localhost:7777/swagger-ui/index.html#/controller/upload

Get answer from PDF: http://localhost:7777/swagger-ui/index.html#/controller/askFromPdf

Get answer from LLM: http://localhost:7777/swagger-ui/index.html#/controller/askFromAILLM


Current Limitations:
* In-memory vector store
* No persistent storage
* No OCR for scanned PDFs
* Basic chunking strategy
* No authentication
* No metadata filtering

Future Improvements
* Add PostgreSQL + PGVector
* Add Elasticsearch/OpenSearch
* Add OCR support
* Add metadata filtering
* Add citation support
* Add streaming responses
* Add UI frontend
* Add multi-document support
* Add role-based access control