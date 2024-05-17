package ru.gb.my.market.core.integrations;

import ru.gb.my.market.api.dtos.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getCart() {
        return cartServiceWebClient.get()
                .uri("api/v1/current-cart")
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCart() {
        cartServiceWebClient.delete()
                .uri("api/v1/current-cart/clear")
                //.header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
