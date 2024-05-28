package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.RefundModel;

import java.util.List;
import java.util.UUID;

public interface RefundRepository {
    UUID save(RefundModel refund);
    RefundModel getByID(UUID id);
    List<RefundModel> getAllByUserID(UUID userID);
}
