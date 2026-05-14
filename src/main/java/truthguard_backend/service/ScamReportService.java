package truthguard_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import truthguard_backend.entity.ScamAnalysis;

//@Service
public class ScamReportService {

    @Autowired
    private ScamAnalysisRepository repository;

    public ScamAnalysis saveAnalysis(ScamAnalysis analysis) {

        return repository.save(analysis);
    }

    public List<ScamAnalysis> getAllReports() {

        return repository.findAll();
    }
}