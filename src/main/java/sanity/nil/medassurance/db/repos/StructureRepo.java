package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sanity.nil.medassurance.db.model.StructureModel;

import java.util.UUID;

public interface StructureRepo extends JpaRepository<StructureModel, UUID> {
}
