package co.zw.santech.registration.service;

import co.zw.santech.registration.dto.UserDTO;
import co.zw.santech.registration.model.User;

import java.util.Optional;

public interface RegistrationService {

    User registerUser(UserDTO user);

    void saveVerificationTokenForUser(String token, User user);

    Integer  validateUserToken(String token);

    Optional<User> findUserByEmail(String email);
}
