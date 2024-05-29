package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.db.repos.OperationRepo;
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

    public List<OperationCardDTO> getAllOperationsByFilters(OperationFilterDTO filters) {
        log.info(String.valueOf(filters));
        return operationRepository.findAllOperationsByFilters(filters);
    }

    public OperationDTO getByID(Integer id) {
        return operationRepository.getByID(id);
    }
}
