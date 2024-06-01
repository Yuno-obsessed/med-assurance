package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sanity.nil.medassurance.db.model.AssuranceModel;
import sanity.nil.medassurance.db.repos.AssuranceRepo;
import sanity.nil.medassurance.interfaces.AssuranceRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AssuranceRepositoryImpl implements AssuranceRepository {

    private final AssuranceRepo assuranceRepo;

    @Override
    public AssuranceModel save(AssuranceModel assurance) {
        return assuranceRepo.save(assurance);
    }

    @Override
    public AssuranceModel getByID(UUID id) {
        return assuranceRepo.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<AssuranceModel> getAllByUserID(UUID userID, Boolean active) {
        return assuranceRepo.findAllByUserID(userID, active);
    }
}
