package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.config.JwtUtils;
import sanity.nil.medassurance.db.mappers.UserMapper;
import sanity.nil.medassurance.db.model.UserModel;
import sanity.nil.medassurance.dto.LoginResponseDTO;
import sanity.nil.medassurance.dto.UserDTO;
import sanity.nil.medassurance.dto.request.LoginDTO;
import sanity.nil.medassurance.interfaces.UserRepository;

import java.util.List;
import java.util.UUID;

import static sanity.nil.medassurance.consts.AuthError.NO_SUCH_USER;
import static sanity.nil.medassurance.consts.AuthError.WRONG_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        UserModel userModel = userRepository.getByEmail(loginDTO.email());
        if (userModel == null) {
            return new LoginResponseDTO(null, NO_SUCH_USER);
        }
        if (!passwordEncoder.matches(loginDTO.password(), userModel.getPassword())) {
            return new LoginResponseDTO(null, WRONG_PASSWORD);
        }

        return new LoginResponseDTO(jwtUtils.generateAccessToken(userModel.getId().toString(), userModel.getEmail(), List.of("ADMIN")), null);
    }

    public UserDTO access() {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userMapper.modelToDTO(userModel);
        return userDTO;
    }
}
