package co.zw.santech.userinterface.remote;

import co.zw.santech.userinterface.model.Response;
import co.zw.santech.userinterface.model.UserDTO;
import co.zw.santech.userinterface.utils.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RegistrationServiceCall {

    private final WebClient.Builder loadBalancedBuilder;

    public RegistrationServiceCall(WebClient.Builder loadBalancedBuilder) {
        this.loadBalancedBuilder = loadBalancedBuilder;
    }

    @CircuitBreaker(name = "registerCb", fallbackMethod = "registerUSerFallBack")
    public Response registerUSer(UserDTO userDTO) {
        Mono<Response> responseMono = registerUSerRemote(userDTO);
        Response response = responseMono.block();
        log.info("::::::After responseMono:::::"+ response);
        return response;
    }

    private Mono<Response> registerUSerRemote(UserDTO userDTO) {
        log.info("Calling Registration service Now::::::<<<>>>");
        return loadBalancedBuilder.build().post().uri(
                Constants.REGISTRATION_URL + "/api/v1/register"
        ).bodyValue(userDTO).retrieve().bodyToMono(Response.class);
    }

    public Response registerUSerFallBack(Exception e) {
        log.info("!!!!!Registration Service is unavailable...Calling FallBack Method NOW!!!!!!");
        log.error("Any errors:::::" + e.getMessage());
        return Response.builder()
                .responseCode("99")
                .successful(Boolean.FALSE)
                .description("REGISTRATION SERVICE UNAVAILABLE")
                .build();
    }
}
