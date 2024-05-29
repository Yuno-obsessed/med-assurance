package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.db.repos.OperationRepo;
import sanity.nil.medassurance.dto.OperationCardDTO;
import sanity.nil.medassurance.dto.request.OperationFilterDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepo operationRepo;

    public List<OperationCardDTO> getAllOperationsByFilters(OperationFilterDTO filters) {
        return operationRepo.findProductsByFilters(filters.doctorName(), filters.structureName(), filters.operationName());
    }
}
