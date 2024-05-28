package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.AssuranceModel;

import java.util.List;
import java.util.UUID;

public interface AssuranceRepository {
    AssuranceModel save(AssuranceModel assurance);
    AssuranceModel getByID(UUID id);
    List<AssuranceModel> getAllByUserID(UUID userID);
}
