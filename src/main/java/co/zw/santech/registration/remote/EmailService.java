package co.zw.santech.registration.remote;

import co.zw.santech.registration.utils.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EmailService {

    private final WebClient.Builder loadBalancedBuilder;

    public EmailService(WebClient.Builder loadBalancedBuilder) {
        this.loadBalancedBuilder = loadBalancedBuilder;
    }

    @CircuitBreaker(name = "emailCb", fallbackMethod = "sendMailFallback")
    public String sendEmail(String email, String msg) {
        Mono<String> emMono = sendMailRemote(email, msg);
        return emMono.block();
    }

    private Mono<String> sendMailRemote(String email, String msg) {
        log.info("Sending Email::::Calling Remote Email Service:::::");
        return loadBalancedBuilder.build().get().uri(
                Constants.EMAIL_SERVICE_URL
                        + "/api/v1/email/" + email + "/" + msg
        ).retrieve().bodyToMono(String.class);
    }

    public String sendMailFallback(Exception e) {
        log.info("Inside Email Fallback::::::>>>");
        log.error(e.getMessage());
        return "EMAIL_SERVICE_NOT_AVAILABLE. Please try Again later on...";
    }
}
