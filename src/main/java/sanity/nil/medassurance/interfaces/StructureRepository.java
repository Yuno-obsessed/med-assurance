package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.dto.StructureDTO;

public interface StructureRepository {

    StructureDTO getByID(Integer id);
}
