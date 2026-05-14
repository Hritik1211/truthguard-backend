package truthguard_backend.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import truthguard_backend.dto.ScamAnalysisResponse;

import java.util.List;

@Service
public class ScamAnalysisService {

    private final ChatClient chatClient;

    public ScamAnalysisService(
            ChatClient.Builder builder
    ) {

        this.chatClient = builder.build();
    }

    public ScamAnalysisResponse analyzeText(String text) {

        try {

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

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }
}