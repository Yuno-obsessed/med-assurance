package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sanity.nil.medassurance.db.mappers.UserMapper;
import sanity.nil.medassurance.dto.request.CreateUserDTO;
import sanity.nil.medassurance.dto.UserDTO;
import sanity.nil.medassurance.interfaces.UserRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UUID save(CreateUserDTO userToCreate) {
        return userRepository.save(userMapper.createDTOToModel(userToCreate));
    }

    public UserDTO getByID(UUID id) {
        return userMapper.modelToDTO(userRepository.getByID(id));
    }

}
