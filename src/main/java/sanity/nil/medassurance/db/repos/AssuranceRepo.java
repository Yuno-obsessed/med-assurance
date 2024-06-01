package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sanity.nil.medassurance.db.model.AssuranceModel;

import java.util.List;
import java.util.UUID;

public interface AssuranceRepo extends JpaRepository<AssuranceModel, UUID> {
    @Query(
            value = "SELECT a.* FROM assurances a " +
                    "WHERE a.user_id = :id " +
                    "AND :active = false " +
                    "OR (:active = true AND a.active_until > now()) ",
            nativeQuery = true
    )
    List<AssuranceModel> findAllByUserID(
            @Param(value = "id") UUID id,
            @Param(value = "active") boolean active
    );
}
