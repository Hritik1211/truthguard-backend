package truthguard_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import truthguard_backend.dto.ScamAnalysisResponse;
import truthguard_backend.service.ScamAnalysisService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UrlScanController {

    @Autowired
    private ScamAnalysisService scamAnalysisService;

    @PostMapping("/scan-url")
    public ScamAnalysisResponse scanUrl(@RequestBody String url) {

        String textToAnalyze = "Analyze this URL for phishing or scam: " + url;

        return scamAnalysisService.analyzeText(textToAnalyze);
    }
}