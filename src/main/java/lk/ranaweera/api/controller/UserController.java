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

    private UserService userService;

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
