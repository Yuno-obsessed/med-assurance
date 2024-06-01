package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sanity.nil.medassurance.db.model.RoleModel;

public interface RoleRepo extends JpaRepository<RoleModel, String> {
    RoleModel findByType(String type);
}
