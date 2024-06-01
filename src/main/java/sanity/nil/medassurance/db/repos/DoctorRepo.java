package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sanity.nil.medassurance.db.model.DoctorModel;

import java.util.List;
import java.util.UUID;

public interface DoctorRepo extends JpaRepository<DoctorModel, Integer> {

    @Query(
            value = "SELECT d.* FROM doctors d " +
                    "LEFT JOIN doctor_operation dop " +
                    "ON d.id = dop.doctor_id " +
                    "LEFT JOIN operations o " +
                    "ON o.id = dop.operation_id " +
                    "WHERE operation_id = :id"
            , nativeQuery = true
    )
    List<DoctorModel> getByOperation(@Param("id") Integer id);
}
