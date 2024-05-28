package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sanity.nil.medassurance.db.model.RefundModel;

import java.sql.Ref;
import java.util.List;
import java.util.UUID;

public interface RefundRepo extends JpaRepository<RefundModel, UUID> {
    @Query(
            value = "SELECT r.* FROM refunds r WHERE r.user_id = :id",
            nativeQuery = true
    )
    List<RefundModel> findAllByUserID(UUID id);
}
