package lk.ranaweera.api.controller;

import jakarta.annotation.security.RolesAllowed;
import lk.ranaweera.api.model.Role;
import lk.ranaweera.api.model.User;
import lk.ranaweera.api.controller.request.UserAuthRequestDTO;
import lk.ranaweera.api.controller.response.UserLoginResponseDTO;
import lk.ranaweera.api.repository.RoleRepository;
import lk.ranaweera.api.repository.UserRepository;
import lk.ranaweera.api.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@AllArgsConstructor
@RestController
public class UserController {

    private final JwtService jwtService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @PostMapping("/authenticate")
    public UserLoginResponseDTO authenticate(@RequestBody UserAuthRequestDTO requestDTO) {
        System.out.println(" ====authenticate user " + requestDTO.getUsername());

        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setName(requestDTO.getName());

        Role role = roleRepository.findById(requestDTO.getRole()).orElseThrow(
                ()->new RuntimeException("df")
        );
        System.out.println("ROLE++++ "+role);

        user.setRoles(List.of(role));

//        user.setRoles(List.of("USER"));
        userRepository.save(user);

//        List<String> roles = List.of("USER", "ADMIN");
        List<String> roles = List.of(role.getName().toUpperCase());
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

//    @RolesAllowed("ROLE_ADMIN")
    @RolesAllowed("ADMIN")
    @GetMapping("/admin")
    public String sayHiAdmin() {

        return "Hi Admin";
    }

//    @RolesAllowed("ROLE_USER")
    @RolesAllowed("USER")
    @GetMapping("/user")
    public String sayHiUser() {

        return "Hi User";
    }
}
