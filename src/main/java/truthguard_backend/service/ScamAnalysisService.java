package truthguard_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import truthguard_backend.dto.ScamAnalysisResponse;

@Service
public class ScamAnalysisService {

    @Value("${GROQ_API_KEY}")
    private String groqApiKey;

    private final OkHttpClient client =
            new OkHttpClient();

    public ScamAnalysisResponse analyzeText(String text) {

        try {

            String prompt = """
Analyze this message for scam detection.

Return ONLY valid JSON in this format:

{
  "scam": true,
  "risk": 90,
  "category": "Phishing",
  "reason": [
    "Urgency language",
    "Suspicious links"
  ]
}

Message:
""" + text;

            String safePrompt = prompt
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");

            String requestJson = """
{
  "model": "llama-3.1-8b-instant",
  "messages": [
    {
      "role": "user",
      "content": "%s"
    }
  ],
  "temperature": 0.2
}
""".formatted(safePrompt);

            RequestBody body = RequestBody.create(
                    requestJson,
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url("https://api.groq.com/openai/v1/chat/completions")
                    .addHeader(
                            "Authorization",
                            "Bearer " + groqApiKey
                    )
                    .addHeader(
                            "Content-Type",
                            "application/json"
                    )
                    .post(body)
                    .build();

            Response response =
                    client.newCall(request).execute();

            String responseBody =
                    response.body().string();

            System.out.println(responseBody);

            ObjectMapper mapper =
                    new ObjectMapper();

            JsonNode root =
                    mapper.readTree(responseBody);

            if (root.has("error")) {

                throw new RuntimeException(
                        root.get("error").toString()
                );
            }

            String aiText = root
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

            System.out.println(aiText);

            int start = aiText.indexOf("{");
            int end = aiText.lastIndexOf("}");

            if (start != -1 && end != -1) {

                aiText = aiText.substring(start, end + 1);
            }

            return mapper.readValue(
                    aiText,
                    ScamAnalysisResponse.class
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }
}