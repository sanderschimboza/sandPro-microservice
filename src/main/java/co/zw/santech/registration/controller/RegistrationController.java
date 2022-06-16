package co.zw.santech.registration.controller;

import co.zw.santech.registration.dto.UserDTO;
import co.zw.santech.registration.events.RegistrationEvent;
import co.zw.santech.registration.model.Response;
import co.zw.santech.registration.model.User;
import co.zw.santech.registration.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/register")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ApplicationEventPublisher publisher;

    public RegistrationController(RegistrationService registrationService,
                                  ApplicationEventPublisher publisher) {
        this.registrationService = registrationService;
        this.publisher = publisher;
    }

    @PostMapping
    public Response registerUser(@RequestBody UserDTO user, HttpServletRequest request) {
        log.info("Received this Registration Request ::::: {}", user);

        Optional<User> userEmail = this.registrationService.findUserByEmail(user.getEmail());

        if (userEmail.isPresent()) {
          return response("05", Boolean.FALSE,
                    "User Email Already Registered");
        }
        User usr = this.registrationService.registerUser(user);
        publisher.publishEvent(new RegistrationEvent(
                usr,
                applicationUrl(request)
        ));
        return response("00", Boolean.TRUE,
                "Account created. Email Sent");
    }

    @GetMapping("/{msg}")
    public ResponseEntity<String> registerTest(@PathVariable("msg") String msg) {
        return ResponseEntity.status(200).body("Registration Controller::::: " + msg);
    }

    private String applicationUrl(HttpServletRequest request) {
        return
                "http://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/" +
                        request.getContextPath();
    }

    private Response response(String responseCode, boolean isSuccessful, String description) {
        return
                Response.builder()
                        .responseCode(responseCode)
                        .successful(isSuccessful)
                        .description(description)
                        .build();
    }
}
