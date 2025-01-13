package lk.ranaweera.api.service.impl;

import lk.ranaweera.api.controller.request.UserAuthRequestDTO;
import lk.ranaweera.api.controller.response.UserLoginResponseDTO;
import lk.ranaweera.api.exception.UserAlreadyRegisteredException;
import lk.ranaweera.api.exception.UserNotFoundException;
import lk.ranaweera.api.model.Role;
import lk.ranaweera.api.model.User;
import lk.ranaweera.api.repository.RoleRepository;
import lk.ranaweera.api.repository.UserRepository;
import lk.ranaweera.api.security.ApplicationConfig;
import lk.ranaweera.api.security.JwtService;
import lk.ranaweera.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationConfig applicationConfig;
    private final RoleRepository roleRepository;
    private JwtService jwtService;

    @Override
    public UserLoginResponseDTO create(UserAuthRequestDTO userAuthRequestDTO) throws UserAlreadyRegisteredException {

        User existingUser = userRepository.findByUsername(userAuthRequestDTO.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyRegisteredException("User Already registered in " + userAuthRequestDTO.getUsername());
        }

        User existingEmailUser = userRepository.findByEmail(userAuthRequestDTO.getEmail());
        if (existingEmailUser != null) {
            throw new UserAlreadyRegisteredException("User Already registered in " + userAuthRequestDTO.getEmail());
        }

        User user = new User();
        user.setName(userAuthRequestDTO.getName());
        user.setUsername(userAuthRequestDTO.getUsername());
        user.setPassword(applicationConfig.passwordEncoder().encode(userAuthRequestDTO.getPassword()));

        Role role = roleRepository.findById(userAuthRequestDTO.getRole()).orElseThrow(
                () -> new RuntimeException("User Role Not Found")
        );
        user.setRoles(List.of(role));
        user.setEmail(userAuthRequestDTO.getEmail());

        userRepository.save(user);

        List<String> roles = List.of("ADMIN");
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", user.getUsername());
        extraClaims.put("password", user.getPassword());
        extraClaims.put("roles", roles);
        extraClaims.put("name", user.getName());

        String token = jwtService.generateToken(user, extraClaims);
        System.out.println("===token " + token);
        return UserLoginResponseDTO.builder()
                .token(token)
                .build();

    }

    @Override
    public UserLoginResponseDTO login(UserAuthRequestDTO userAuthRequestDTO) throws UserNotFoundException {

        User user = userRepository.findByUsername(userAuthRequestDTO.getUsername());

        if (user != null) {
            if (!applicationConfig.passwordEncoder().matches(userAuthRequestDTO.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid username or password");
            }

            List<String> roles = List.of("ADMIN");
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("username", user.getUsername());
            extraClaims.put("password", user.getPassword());
            extraClaims.put("roles", roles);
            extraClaims.put("name", user.getName());

            String token = jwtService.generateToken(user, extraClaims);
            System.out.println("===token " + token);
            return UserLoginResponseDTO.builder()
                    .token(token)
                    .build();
        }

        else {
            throw new UserNotFoundException("User not found with username " + userAuthRequestDTO.getUsername());
        }
    }

//    private List<Role> getUserRoles(String username) {
//        User user = userRepository.findByUsernameWithRoles(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return user.getRoles();
//    }
}
