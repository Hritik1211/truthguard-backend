package truthguard_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import truthguard_backend.dto.ScamAnalysisResponse;
import truthguard_backend.service.OcrService;
import truthguard_backend.service.ScamAnalysisService;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ScamReportController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    private ScamAnalysisService scamAnalysisService;

    // EMAIL SCANNER
    @PostMapping("/scan-email")
    public ScamAnalysisResponse scanEmail(
            @RequestBody String text
    ) {

        try {

            return scamAnalysisService
                    .analyzeText(text);

        } catch (Exception e) {

            e.printStackTrace();

            return new ScamAnalysisResponse(
                    true,
                    100,
                    "Email Scan Error",
                    List.of(
                            "Failed to analyze email"
                    )
            );
        }
    }

    // URL SCANNER
    @PostMapping("/scan-url")
    public ScamAnalysisResponse scanUrl(
            @RequestBody String url
    ) {

        try {

            return scamAnalysisService
                    .analyzeText(url);

        } catch (Exception e) {

            e.printStackTrace();

            return new ScamAnalysisResponse(
                    true,
                    100,
                    "URL Scan Error",
                    List.of(
                            "Failed to analyze URL"
                    )
            );
        }
    }

    // IMAGE SCANNER
    @PostMapping("/scan-image")
    public ScamAnalysisResponse scanImage(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            File convFile =
                    File.createTempFile(
                            "upload",
                            ".png"
                    );

            file.transferTo(convFile);

            String extractedText =
                    ocrService.extractText(convFile);

            System.out.println(
                    "OCR TEXT: " + extractedText
            );

            if (
                    extractedText == null ||
                            extractedText.isBlank()
            ) {

                return new ScamAnalysisResponse(
                        true,
                        90,
                        "No Text Found",
                        List.of(
                                "Could not detect readable text in image"
                        )
                );
            }

            ScamAnalysisResponse result =
                    scamAnalysisService
                            .analyzeText(extractedText);

            convFile.delete();

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return new ScamAnalysisResponse(
                    true,
                    100,
                    "Image Scan Error",
                    List.of(
                            "Failed to analyze image"
                    )
            );
        }
    }

    @GetMapping("/")
    public String home() {

        return "TruthGuard Backend Running";
    }

    @GetMapping("/history")
    public String getHistory() {

        return "Database disabled";
    }
}