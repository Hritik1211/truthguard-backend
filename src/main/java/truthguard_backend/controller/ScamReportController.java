/*package truthguard_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import truthguard_backend.dto.ScamAnalysisResponse;
import truthguard_backend.entity.ScamAnalysis;
import truthguard_backend.repository.ScamAnalysisRepository;
import truthguard_backend.service.OcrService;
import truthguard_backend.service.ScamAnalysisService;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

//@RestController
//@RequestMapping("/api")
@CrossOrigin("*")
public class ScamReportController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    private ScamAnalysisService scamAnalysisService;

   // @Autowired
   // private ScamAnalysisRepository analysisRepository;


    // EMAIL SCAM DETECTION
    @PostMapping("/scan-email")
    public ScamAnalysisResponse scanEmail(
            @RequestBody String text
    ) {

        ScamAnalysisResponse aiResult =
                scamAnalysisService.analyzeText(text);

        // SAVE TO DATABASE
        ScamAnalysis analysis = new ScamAnalysis();

        analysis.setType("EMAIL");

        analysis.setContent(text);

        analysis.setResult(
                aiResult.isScam() ? "SCAM" : "SAFE"
        );

        analysis.setRisk(aiResult.getRisk());

        analysis.setScannedAt(LocalDateTime.now());

       // analysisRepository.save(analysis);

        return aiResult;
    }


    // IMAGE SCAM DETECTION
    @PostMapping("/scan-image")
    public ScamAnalysisResponse scanImage(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            System.out.println("IMAGE RECEIVED");

            File convFile =
                    File.createTempFile("upload", ".png");

            file.transferTo(convFile);

            System.out.println("FILE SAVED");

            String extractedText =
                    ocrService.extractText(convFile);

            System.out.println(
                    "OCR TEXT: " + extractedText
            );

            if (extractedText == null
                    || extractedText.isBlank()) {

                throw new RuntimeException(
                        "No text extracted from image"
                );
            }

            ScamAnalysisResponse aiResult =
                    scamAnalysisService.analyzeText(extractedText);

            System.out.println(
                    "AI RESULT GENERATED"
            );

            // SAVE TO DATABASE
            ScamAnalysis analysis = new ScamAnalysis();

            analysis.setType("IMAGE");

            analysis.setContent(extractedText);

            analysis.setResult(
                    aiResult.isScam() ? "SCAM" : "SAFE"
            );

            analysis.setRisk(aiResult.getRisk());

            analysis.setScannedAt(LocalDateTime.now());

            analysisRepository.save(analysis);

            return aiResult;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Image scan failed"
            );
        }
    }


    // SCAN HISTORY
    @GetMapping("/history")
    public List<ScamAnalysis> getHistory() {

       // return analysisRepository.findAll();
    }

}