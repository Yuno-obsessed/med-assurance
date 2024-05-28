package sanity.nil.medassurance.db.mappers;

import org.mapstruct.Mapper;
import sanity.nil.medassurance.db.model.AssuranceModel;
import sanity.nil.medassurance.dto.AssuranceDTO;
import sanity.nil.medassurance.dto.request.CreateAssuranceDTO;

@Mapper(componentModel = "spring")
public interface AssuranceMapper {
    AssuranceDTO modelToDTO(AssuranceModel assuranceModel);
    AssuranceModel createDTOToModel(CreateAssuranceDTO createAssuranceDTO);
}
