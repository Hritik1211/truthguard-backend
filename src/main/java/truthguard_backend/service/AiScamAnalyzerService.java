package truthguard_backend.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiScamAnalyzerService {

    private final ChatClient chatClient;

    public AiScamAnalyzerService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String analyzeScam(String inputText) {

        String prompt = """
You are an advanced AI Scam Detection System.

Analyze the text or URL carefully.

Detect:
- phishing
- fake banking links
- OTP scams
- job scams
- investment scams
- suspicious domains
- shortened URLs
- urgent manipulation tactics

Return output EXACTLY in this format:

Scam Score: <number>

Risk Level: <LOW/MEDIUM/HIGH>

Scam Type: <type>

Reason: <reason>

Safety Advice: <advice>

TEXT:
""" + inputText;
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}