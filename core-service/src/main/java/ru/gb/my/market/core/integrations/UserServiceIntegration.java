package ru.gb.my.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserServiceIntegration {
    private final WebClient userServiceWebClient;

    public String getUserByUsername(String username) {
        return userServiceWebClient.get()
                .uri("api/v1/users")
                .header("username", username)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
