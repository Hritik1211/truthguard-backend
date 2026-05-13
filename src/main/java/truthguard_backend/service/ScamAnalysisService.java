package truthguard_backend.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import truthguard_backend.dto.ScamAnalysisResponse;
import truthguard_backend.entity.ScamAnalysis;
import truthguard_backend.repository.ScamAnalysisRepository;

import java.time.LocalDateTime;

@Service
public class ScamAnalysisService {

    private final ChatClient chatClient;

    private final ScamAnalysisRepository repository;

    public ScamAnalysisService(
            ChatClient.Builder builder,
            ScamAnalysisRepository repository
    ) {

        this.chatClient = builder.build();

        this.repository = repository;
    }

    public ScamAnalysisResponse analyzeText(String text) {

        try {

            String prompt = """
Analyze this text for scam detection.

Return ONLY valid JSON.

Format:

{
  "scam": true,
  "risk": 90,
  "category": "Phishing",
  "reason": [
    "Suspicious link",
    "Urgency language",
    "Unknown sender"
  ]
}

Text:
""" + text;

            String response = chatClient.prompt(prompt)
                    .call()
                    .content();

            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(
                    MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
                    true
            );

            ScamAnalysisResponse result =
                    mapper.readValue(response, ScamAnalysisResponse.class);

            // SAVE TO DATABASE
            ScamAnalysis analysis = new ScamAnalysis();

            analysis.setType("TEXT");

            analysis.setContent(text);

            analysis.setResult(
                    result.isScam() ? "SCAM" : "SAFE"
            );

            analysis.setRisk(result.getRisk());

            analysis.setScannedAt(LocalDateTime.now());

            repository.save(analysis);

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}