package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sanity.nil.medassurance.db.model.RefundModel;
import sanity.nil.medassurance.db.repos.RefundRepo;
import sanity.nil.medassurance.dto.RefundFilterDTO;
import sanity.nil.medassurance.interfaces.RefundRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {

    private final RefundRepo refundRepo;

    @Override
    public RefundModel save(RefundModel refund) {
        return refundRepo.save(refund);
    }

    @Override
    public RefundModel getByID(UUID id) {
        return refundRepo.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<RefundModel> getAllByUserID(RefundFilterDTO filters) {
        Pageable pageable = PageRequest.of(filters.offset(), filters.limit());
        return refundRepo.findAllByUserID(filters.userID(), pageable);
    }

    @Override
    public int countByUserID(UUID userID) {
        return refundRepo.countByUserID(userID);
    }
}
