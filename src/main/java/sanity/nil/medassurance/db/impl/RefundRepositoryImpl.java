package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sanity.nil.medassurance.db.model.RefundModel;
import sanity.nil.medassurance.db.repos.RefundRepo;
import sanity.nil.medassurance.interfaces.RefundRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {

    private final RefundRepo refundRepo;

    @Override
    public UUID save(RefundModel refund) {
        return refundRepo.save(refund).getId();
    }

    @Override
    public RefundModel getByID(UUID id) {
        return refundRepo.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<RefundModel> getAllByUserID(UUID userID) {
        return refundRepo.findAllByUserID(userID);
    }
}
