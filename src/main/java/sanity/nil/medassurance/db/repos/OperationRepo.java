package sanity.nil.medassurance.db.repos;

import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.dto.OperationCardDTO;
import sanity.nil.medassurance.dto.request.OperationFilterDTO;

import java.util.List;
import java.util.UUID;

public interface OperationRepo extends JpaRepository<OperationModel, Integer> {

//    @Query(
//            value = "SELECT o.name as operationName, s.name as structureName, d.name as doctorName, dop.price as price FROM operations o " +
//                    "LEFT JOIN doctor_operation dop ON dop.operation_id = o.id " +
//                    "LEFT JOIN doctors d ON dop.doctor_id = d.id " +
//                    "LEFT JOIN structures s ON d.structure_id = s.id " +
//                    "WHERE (:doctorName IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :doctorName, '%'))) " +
//                    "AND (:structureName IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :structureName, '%'))) " +
//                    "AND (:operationName IS NULL OR LOWER(o.name) LIKE LOWER(CONCAT('%', :operationName, '%'))) " +
//                    "ORDER BY dop.price ASC",
//            nativeQuery = true
//    )

    @Query(
            value = "SELECT new sanity.nil.medassurance.dto.OperationCardDTO(o.name, s.name, s.id, d.name, d.id, doc_op.price) " +
                    "FROM OperationModel o " +
                    "LEFT JOIN DoctorOperationModel doc_op ON doc_op.operation = o " +
                    "LEFT JOIN DoctorModel d ON doc_op.doctor = d " +
                    "LEFT JOIN StructureModel s ON d.structure = s " +
                    "WHERE (:doctorName IS NULL OR LOWER(CAST(d.name AS text)) LIKE LOWER(CAST(CONCAT('%', :doctorName, '%') AS text))) " +
                    "AND (:structureName IS NULL OR LOWER(CAST(s.name AS text)) LIKE LOWER(CAST(CONCAT('%', :structureName, '%') AS text))) " +
                    "AND (:operationName IS NULL OR LOWER(CAST(o.name AS text)) LIKE LOWER(CAST(CONCAT('%', :operationName, '%') AS text))) " +
                    "ORDER BY doc_op.price ASC",
            nativeQuery = false
    )
    List<OperationCardDTO> findProductsByFilters(
            @Param("doctorName") String doctorName,
            @Param("structureName") String structureName,
            @Param("operationName") String operationName,
            Pageable pageable
    );
}
