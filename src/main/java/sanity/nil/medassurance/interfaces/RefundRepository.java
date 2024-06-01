package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.RefundModel;
import sanity.nil.medassurance.dto.RefundFilterDTO;

import java.util.List;
import java.util.UUID;

public interface RefundRepository {
    RefundModel save(RefundModel refund);
    RefundModel getByID(UUID id);
    List<RefundModel> getAllByUserID(RefundFilterDTO filter);
    int countByUserID(UUID userID);
}
