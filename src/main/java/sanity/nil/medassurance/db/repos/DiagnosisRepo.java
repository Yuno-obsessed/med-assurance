package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sanity.nil.medassurance.db.model.DiagnosisModel;

import java.util.List;
import java.util.UUID;

public interface DiagnosisRepo extends JpaRepository<DiagnosisModel, UUID> {

    @Query(
            "SELECT d FROM DiagnosisModel d"
    )
    List<DiagnosisModel> getAll();
}
