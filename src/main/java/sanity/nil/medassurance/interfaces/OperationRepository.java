package sanity.nil.medassurance.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.dto.OperationCardDTO;
import sanity.nil.medassurance.dto.OperationDTO;
import sanity.nil.medassurance.dto.request.OperationFilterDTO;

import java.util.List;

public interface OperationRepository {

    OperationModel getByID(Integer id);

    List<OperationCardDTO> findAllOperationsByFilters(OperationFilterDTO filters);

    int countByFilters(OperationFilterDTO filters);
    List<OperationModel> getAll();
}
