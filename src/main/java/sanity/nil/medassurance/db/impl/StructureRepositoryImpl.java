package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sanity.nil.medassurance.db.model.StructureModel;
import sanity.nil.medassurance.db.repos.StructureRepo;
import sanity.nil.medassurance.dto.StructureDTO;
import sanity.nil.medassurance.interfaces.StructureRepository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class StructureRepositoryImpl implements StructureRepository {

    private final StructureRepo structureRepo;

    @Override
    public StructureDTO getByID(Integer id) {
        StructureModel structure = structureRepo.findById(id).orElseThrow(NoSuchElementException::new);
        return new StructureDTO(structure.getName(), structure.getAddress());
    }
}
