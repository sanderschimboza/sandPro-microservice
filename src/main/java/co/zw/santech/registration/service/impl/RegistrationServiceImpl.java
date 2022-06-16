package co.zw.santech.registration.service.impl;

import co.zw.santech.registration.dto.UserDTO;
import co.zw.santech.registration.model.Role;
import co.zw.santech.registration.model.User;
import co.zw.santech.registration.model.VerificationToken;
import co.zw.santech.registration.repo.RoleRepository;
import co.zw.santech.registration.repo.UserRepository;
import co.zw.santech.registration.repo.VerificationTokenRepository;
import co.zw.santech.registration.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Optional;

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    public RegistrationServiceImpl(RoleRepository roleRepository,
                                   BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
                                   VerificationTokenRepository verificationTokenRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(passwordEncoder.encode(userDTO.getMatchingPassword()))
                .isEnabled(Boolean.FALSE)
                .role(this.roleRepository.findById(2L).orElse(Role.builder()
                        .id(2L)
                        .name("ROLE_USER")
                        .build()))
                .build();

        this.userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        Optional<VerificationToken> verificationToken1 =
                this.verificationTokenRepository.findTokenByUserId(user.getId());
        verificationToken1.ifPresent(this.verificationTokenRepository::delete);

        VerificationToken verificationToken =
                new VerificationToken(user, token);
        this.verificationTokenRepository.save(verificationToken);
    }

    @Override
    @Transactional
    public Integer validateUserToken(String token) {
        Optional<VerificationToken> verificationToken =
                this.verificationTokenRepository.findByToken(token);

        if (verificationToken.isEmpty()) {
            log.error("Invalid Token....Token not found::: {}", token);
            return 404;
        }

        User user = verificationToken.get().getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.get().getExpirationTime().getTime() -
                cal.getTime().getTime() <= 0)) {
            log.error("Expired Token:::: {}", token);
            this.verificationTokenRepository.delete(verificationToken.get());
            return 300;
        }
        log.info("Token accepted::::{}", token);
        user.setIsEnabled(Boolean.TRUE);
        this.verificationTokenRepository.delete(verificationToken.get());
        return 200;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }
}
