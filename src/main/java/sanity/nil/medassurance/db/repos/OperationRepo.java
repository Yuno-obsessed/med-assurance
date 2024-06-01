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


    @Query(
            value = "SELECT count(*) " +
                    "FROM OperationModel o " +
                    "LEFT JOIN DoctorOperationModel doc_op ON doc_op.operation = o " +
                    "LEFT JOIN DoctorModel d ON doc_op.doctor = d " +
                    "LEFT JOIN StructureModel s ON d.structure = s " +
                    "WHERE (:doctorName IS NULL OR LOWER(CAST(d.name AS text)) LIKE LOWER(CAST(CONCAT('%', :doctorName, '%') AS text))) " +
                    "AND (:structureName IS NULL OR LOWER(CAST(s.name AS text)) LIKE LOWER(CAST(CONCAT('%', :structureName, '%') AS text))) " +
                    "AND (:operationName IS NULL OR LOWER(CAST(o.name AS text)) LIKE LOWER(CAST(CONCAT('%', :operationName, '%') AS text))) ",
            nativeQuery = false
    )
    int countByFilters(
            @Param("doctorName") String doctorName,
            @Param("structureName") String structureName,
            @Param("operationName") String operationName
    );

    @Query(
            "SELECT o FROM OperationModel o"
    )
    List<OperationModel> getAll();
}
