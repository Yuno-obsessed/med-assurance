package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.db.repos.OperationRepo;
import sanity.nil.medassurance.dto.FilteredOperationsDTO;
import sanity.nil.medassurance.dto.OperationCardDTO;
import sanity.nil.medassurance.dto.OperationDTO;
import sanity.nil.medassurance.dto.request.OperationFilterDTO;
import sanity.nil.medassurance.interfaces.OperationRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    public FilteredOperationsDTO getAllOperationsByFilters(OperationFilterDTO filters) {
        List<OperationCardDTO> operations = operationRepository.findAllOperationsByFilters(filters);
        int totalPages = operationRepository.countByFilters(filters);
        if (totalPages % filters.limit() == 0) {
            totalPages = totalPages / filters.limit();
        } else {
            totalPages = totalPages / filters.limit() + 1;
        }
        return new FilteredOperationsDTO(operations, totalPages, filters.offset()+1);
    }

    public List<OperationDTO> getAllOperations() {
        return operationRepository.getAll().stream()
                .map(e -> new OperationDTO(e.getId(), e.getName())).toList();
    }
}
