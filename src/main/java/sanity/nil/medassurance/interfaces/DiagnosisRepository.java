package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.DiagnosisModel;

import java.util.UUID;

public interface DiagnosisRepository {
    UUID save(DiagnosisModel diagnosis);
    DiagnosisModel getByID(UUID id);
}
