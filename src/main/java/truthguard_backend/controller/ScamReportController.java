package truthguard_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import truthguard_backend.dto.ScamAnalysisResponse;
import truthguard_backend.service.ScamAnalysisService;

import java.io.File;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ScamReportController {

    @Autowired
    private ScamAnalysisService scamAnalysisService;

    // EMAIL SCAN
    @PostMapping("/scan-email")
    public ScamAnalysisResponse scanEmail(
            @RequestBody String text
    ) {

        System.out.println("EMAIL SCAN HIT");

        return scamAnalysisService.analyzeText(text);
    }

    // URL SCAN
    @PostMapping("/scan-url")
    public ScamAnalysisResponse scanUrl(
            @RequestBody String url
    ) {

        System.out.println("URL SCAN HIT");

        return scamAnalysisService.analyzeText(url);
    }

    // IMAGE SCAN
    @PostMapping("/scan-image")
    public ScamAnalysisResponse scanImage(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            System.out.println("IMAGE SCAN HIT");

            File convFile =
                    File.createTempFile("upload", ".png");

            file.transferTo(convFile);

            // TEMPORARY MOCK TEXT
            String extractedText =
                    "Urgent PayPal verification required. Click suspicious link now.";

            return scamAnalysisService
                    .analyzeText(extractedText);

        } catch (Exception e) {

            e.printStackTrace();

            return new ScamAnalysisResponse(
                    true,
                    100,
                    "System Error",
                    java.util.List.of(
                            e.getMessage()
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