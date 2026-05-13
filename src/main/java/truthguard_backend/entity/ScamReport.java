package truthguard_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ScamReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String inputText;

    private String riskLevel;

    private int scamScore;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getScamScore() {
        return scamScore;
    }

    public void setScamScore(int scamScore) {
        this.scamScore = scamScore;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}