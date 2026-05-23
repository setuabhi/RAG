package com.rag;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api") // Sets the base URL path correctly
public class Controller {

    private final RagService ragService;

    // Note: @Autowired is optional if the class only has one constructor
    public Controller(RagService ragService) {
        this.ragService=ragService;
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String upload(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        ragService.processPdf(file);

        return "PDF processed successfully";
    }

    @GetMapping("/askFromPdf")
    public String askFromPdf(@RequestParam(name = "question") String question) {

        return ragService.askFromPDF(question);
    }

    @GetMapping("/askFromAILLM")
    public String askFromAILLM(@RequestParam(name = "question") String question) {
        return ragService.askFromLLM(question);
    }
}
