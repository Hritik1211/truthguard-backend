package truthguard_backend.dto;

import java.util.List;

public class ScamAnalysisResponse {

    private boolean scam;

    private int risk;

    private String category;

    private List<String> reason;

    // Empty Constructor
    public ScamAnalysisResponse() {
    }

    // Full Constructor
    public ScamAnalysisResponse(
            boolean scam,
            int risk,
            String category,
            List<String> reason
    ) {
        this.scam = scam;
        this.risk = risk;
        this.category = category;
        this.reason = reason;
    }

    // Getter and Setter for scam
    public boolean isScam() {
        return scam;
    }

    public void setScam(boolean scam) {
        this.scam = scam;
    }

    // Getter and Setter for risk
    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

    // Getter and Setter for category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter and Setter for reason
    public List<String> getReason() {
        return reason;
    }

    public void setReason(List<String> reason) {
        this.reason = reason;
    }
}