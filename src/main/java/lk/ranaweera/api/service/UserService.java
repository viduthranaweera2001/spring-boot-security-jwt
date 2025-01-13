package lk.ranaweera.api.service;

import lk.ranaweera.api.controller.request.UserAuthRequestDTO;
import lk.ranaweera.api.controller.response.UserLoginResponseDTO;
import lk.ranaweera.api.exception.UserAlreadyRegisteredException;
import lk.ranaweera.api.exception.UserNotFoundException;

public interface UserService {
    UserLoginResponseDTO create(UserAuthRequestDTO userAuthRequestDTO) throws UserAlreadyRegisteredException;
    UserLoginResponseDTO login(UserAuthRequestDTO userAuthRequestDTO) throws UserNotFoundException;
}
