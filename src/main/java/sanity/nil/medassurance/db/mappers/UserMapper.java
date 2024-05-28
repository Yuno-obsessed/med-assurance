package sanity.nil.medassurance.db.mappers;

import org.mapstruct.Mapper;
import sanity.nil.medassurance.db.model.UserModel;
import sanity.nil.medassurance.dto.request.CreateUserDTO;
import sanity.nil.medassurance.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel dtoToModel(UserDTO userDTO);
    UserDTO modelToDTO(UserModel userModel);
    UserModel createDTOToModel(CreateUserDTO createUserDTO);
}
