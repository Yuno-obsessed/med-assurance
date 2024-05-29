package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.dto.StructureDTO;
import sanity.nil.medassurance.interfaces.StructureRepository;

@Service
@RequiredArgsConstructor
public class StructureService {

    private final StructureRepository structureRepository;

    public StructureDTO getByID(Integer id) {
        return structureRepository.getByID(id);
    }
}
