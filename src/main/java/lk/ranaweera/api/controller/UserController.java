package lk.ranaweera.api.controller;

import jakarta.annotation.security.RolesAllowed;
import lk.ranaweera.api.controller.response.MessageResponse;
import lk.ranaweera.api.controller.response.UserLoginResponseDTO;
import lk.ranaweera.api.exception.UserAlreadyRegisteredException;
import lk.ranaweera.api.exception.UserNotFoundException;
import lk.ranaweera.api.controller.request.UserAuthRequestDTO;
import lk.ranaweera.api.security.JwtService;
import lk.ranaweera.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController {

    private final JwtService jwtService;
    private UserService userService;
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;

//    @PostMapping("/authenticate")
//    public UserLoginResponseDTO authenticate(@RequestBody UserAuthRequestDTO requestDTO) {
//        System.out.println(" ====authenticate user " + requestDTO.getUsername());
//
//        User user = new User();
//        user.setUsername(requestDTO.getUsername());
//        user.setPassword(requestDTO.getPassword());
//        user.setName(requestDTO.getName());
//
//        Role role = roleRepository.findById(requestDTO.getRole()).orElseThrow(
//                ()->new RuntimeException()
//        );
//
//        user.setRoles(List.of(role));
//        userRepository.save(user);
//
//        List<String> roles = List.of(role.getName().toUpperCase());
//        Map<String, Object> extraClaims = new HashMap<>();
//        extraClaims.put("username", user.getUsername());
//        extraClaims.put("password", user.getPassword());
//        extraClaims.put("roles", roles);
//        extraClaims.put("name", user.getName());
//
//        String token = jwtService.generateToken(user, extraClaims);
//        System.out.println("===token " + token);
//        return UserLoginResponseDTO.builder()
//                .token(token)
//                .build();
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserLoginResponseDTO> authenticate(@RequestBody UserAuthRequestDTO userAuthRequestDTO) throws UserAlreadyRegisteredException {

        UserLoginResponseDTO userLoginResponseDTO = userService.create(userAuthRequestDTO);
        MessageResponse messageResponse = MessageResponse.builder()
                .message("User Registered successfully!")
                .build();

        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserAuthRequestDTO userAuthRequestDTO) throws UserNotFoundException {

        UserLoginResponseDTO userLoginResponseDTO = userService.login(userAuthRequestDTO);

        return new ResponseEntity<>(userLoginResponseDTO,HttpStatus.OK);
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
