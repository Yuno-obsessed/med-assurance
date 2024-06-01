package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sanity.nil.medassurance.db.model.DoctorOperationKey;
import sanity.nil.medassurance.db.model.DoctorOperationModel;

import java.util.Optional;

public interface DoctorOperationRepo extends JpaRepository<DoctorOperationModel, DoctorOperationKey> {

    @Query(
            value = "SELECT * FROM doctor_operation " +
                    "WHERE operation_id = :operationID " +
                    "AND doctor_id = :doctorID",
            nativeQuery = true
    )
    Optional<DoctorOperationModel> getByIds(
            @Param(value = "doctorID") Integer doctorID,
            @Param(value = "operationID") Integer operationID
    );
}
