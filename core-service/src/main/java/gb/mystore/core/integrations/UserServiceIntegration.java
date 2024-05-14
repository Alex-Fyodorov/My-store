package gb.mystore.core.integrations;

import gb.mystore.api.dtos.CartDto;
import gb.mystore.api.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserServiceIntegration {
    private final WebClient userServiceWebClient;

// Этот класс и вся остальная интеграция core-service с auth-service пока что лишние.

    public String getUserByUsername(String username) {
        return userServiceWebClient.get()
                .uri("api/v1/users")
                .header("username", username)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
