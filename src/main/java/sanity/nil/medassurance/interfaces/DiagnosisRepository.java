package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.DiagnosisModel;
import sanity.nil.medassurance.dto.DiagnosisDTO;

import java.util.List;
import java.util.UUID;

public interface DiagnosisRepository {
    void save(DiagnosisModel diagnosis);
    DiagnosisModel getByID(UUID id);
    List<DiagnosisModel> getAll();
}
