package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.db.repos.OperationRepo;
import sanity.nil.medassurance.dto.OperationCardDTO;
import sanity.nil.medassurance.dto.OperationDTO;
import sanity.nil.medassurance.dto.request.OperationFilterDTO;
import sanity.nil.medassurance.interfaces.OperationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class OperationRepositoryImpl implements OperationRepository {

    private final OperationRepo operationRepo;

    @Override
    public OperationDTO getByID(Integer id) {
        OperationModel operation = operationRepo.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return new OperationDTO(operation.getName(), operation.getDescription());
    }

    @Override
    public List<OperationCardDTO> findAllOperationsByFilters(OperationFilterDTO filters) {
        Pageable pageable = PageRequest.of(filters.offset(), filters.limit());
        return operationRepo.findProductsByFilters(filters.doctorName(), filters.structureName(), filters.operationName(), pageable);
    }
}
