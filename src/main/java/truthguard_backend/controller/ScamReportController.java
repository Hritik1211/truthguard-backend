package truthguard_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import truthguard_backend.dto.ScamAnalysisResponse;
import truthguard_backend.service.OcrService;
import truthguard_backend.service.ScamAnalysisService;

import java.io.File;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ScamReportController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    private ScamAnalysisService scamAnalysisService;

    @PostMapping("/scan-email")
    public ScamAnalysisResponse scanEmail(@RequestBody String text) {

        return scamAnalysisService.analyzeText(text);
    }

    @PostMapping("/scan-image")
    public ScamAnalysisResponse scanImage(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            File convFile = File.createTempFile("upload", ".png");

            file.transferTo(convFile);

            String extractedText =
                    ocrService.extractText(convFile);

            if (extractedText == null
                    || extractedText.isBlank()) {

                throw new RuntimeException(
                        "No text extracted from image"
                );
            }

            return scamAnalysisService
                    .analyzeText(extractedText);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Image scan failed"
            );
        }
    }

    @GetMapping("/history")
    public String getHistory() {

        return "Database disabled";
    }
}