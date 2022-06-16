package co.zw.santech.registration.listeners;

import co.zw.santech.registration.events.RegistrationEvent;
import co.zw.santech.registration.model.User;
import co.zw.santech.registration.remote.EmailService;
import co.zw.santech.registration.service.RegistrationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationEventLister implements ApplicationListener<RegistrationEvent> {

    private final RegistrationService userService;
    private final EmailService emailService;

    public RegistrationEventLister(RegistrationService userService,
                                   EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(RegistrationEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        this.userService.saveVerificationTokenForUser(token, user);

        String url = event.getUrl()
                + "verifyRegistration?token=" + token;

        String emailBody = "<img src='/images/sp.png'" +
                " alt='SandPro logo'>\n\n <h3>Activate your account</h3>\n\n Hello "
                + event.getUser().getFirstName()
                + ",<br><br> This message confirms that the following user profile was successfully created: <br>" +

                "Email: " + event.getUser().getEmail() + "<br> " +
                "\nFirst Name: " + event.getUser().getFirstName() + "<br> " +
                "\nLast Name: " + event.getUser().getLastName() + "<br><br>" +
                "\n<a href=" + url + "> Click here to activate your account</a> <br>" +
                "\n\nSincerely,<br><br>" +
                "<b>The SandPro Team</b>";

        String emailSendResponse = this.emailService
                .sendEmail(event.getUser().getEmail(), emailBody);
        log.info(emailSendResponse);
    }
}
