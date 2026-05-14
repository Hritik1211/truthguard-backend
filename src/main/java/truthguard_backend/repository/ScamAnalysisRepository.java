package truthguard_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truthguard_backend.entity.ScamAnalysis;

@Repository
public interface ScamAnalysisRepository extends JpaRepository<ScamAnalysis, Long> {
}
