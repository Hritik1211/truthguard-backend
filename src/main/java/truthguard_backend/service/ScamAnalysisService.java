package truthguard_backend.service;

import org.springframework.stereotype.Service;

import truthguard_backend.dto.ScamAnalysisResponse;

import java.util.List;

@Service
public class ScamAnalysisService {

    public ScamAnalysisResponse analyzeText(String text) {

        ScamAnalysisResponse response =
                new ScamAnalysisResponse();

        response.setScam(true);

        response.setRisk(90);

        response.setCategory("Phishing");

        response.setReason(
                List.of(
                        "Urgency language detected",
                        "Suspicious message"
                )
        );

        return response;
    }
}