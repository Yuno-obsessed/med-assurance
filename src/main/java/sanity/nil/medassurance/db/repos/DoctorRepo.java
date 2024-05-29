package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sanity.nil.medassurance.db.model.DoctorModel;

import java.util.UUID;

public interface DoctorRepo extends JpaRepository<DoctorModel, Integer> {
}
