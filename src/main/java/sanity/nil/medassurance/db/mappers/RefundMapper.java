package sanity.nil.medassurance.db.mappers;

import org.mapstruct.Mapper;
import sanity.nil.medassurance.db.model.RefundModel;
import sanity.nil.medassurance.dto.request.CreateRefundDTO;
import sanity.nil.medassurance.dto.RefundDTO;

@Mapper(componentModel = "spring")
public interface RefundMapper {
    RefundDTO modelToDTO(RefundModel refundModel);
    RefundModel createDTOToModel(CreateRefundDTO createRefundDTO);
}
