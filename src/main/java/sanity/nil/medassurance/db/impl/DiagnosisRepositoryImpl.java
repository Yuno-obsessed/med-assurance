package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.db.model.DiagnosisModel;
import sanity.nil.medassurance.db.repos.DiagnosisRepo;
import sanity.nil.medassurance.interfaces.DiagnosisRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiagnosisRepositoryImpl implements DiagnosisRepository {

    private final DiagnosisRepo diagnosisRepo;

    @Override
    public void save(DiagnosisModel diagnosis) {
        diagnosisRepo.save(diagnosis);
    }

    @Override
    public DiagnosisModel getByID(UUID id) {
        return diagnosisRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<DiagnosisModel> getAll() {
        return diagnosisRepo.getAll();
    }
}
