package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sanity.nil.medassurance.db.mappers.UserMapper;
import sanity.nil.medassurance.db.model.RoleModel;
import sanity.nil.medassurance.db.model.UserModel;
import sanity.nil.medassurance.db.repos.RoleRepo;
import sanity.nil.medassurance.dto.request.CreateUserDTO;
import sanity.nil.medassurance.dto.UserDTO;
import sanity.nil.medassurance.exceptions.DuplicatedUserEntryException;
import sanity.nil.medassurance.interfaces.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UUID save(CreateUserDTO userToCreate) {
        UserModel userModel = userMapper.createDTOToModel(userToCreate);
        if (userModel.getRoles() == null || userModel.getRoles().isEmpty()) {
            Set<RoleModel> defaultRoles = new HashSet<>();
            RoleModel defaultRole = roleRepo.findByType("ROLE_USER");
            defaultRoles.add(defaultRole);
            userModel.setRoles(defaultRoles);
        }
        userModel.setPassword(passwordEncoder.encode(userToCreate.password()));
        try {
            return userRepository.save(userModel);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                String constraintName = ((org.hibernate.exception.ConstraintViolationException) e.getCause()).getConstraintName();
                if ("users_iban_key".equals(constraintName)) {
                    throw new DuplicatedUserEntryException("iban");
                } else if ("users_email_key".equals(constraintName)) {
                    throw new DuplicatedUserEntryException("email");
                }
            }
            throw e;
        }
    }

    public UserDTO getByID(UUID id) {
        return userMapper.modelToDTO(userRepository.getByID(id));
    }

}
