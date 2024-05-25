package ru.gb.my.market.core.integrations;

import org.springframework.web.bind.annotation.PathVariable;
import ru.gb.my.market.api.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getCart(String username, String cartId) {
        return cartServiceWebClient.get()
                .uri("api/v1/current-cart/" + cartId)
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCart(String username) {
        cartServiceWebClient.delete()
                .uri("api/v1/current-cart/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
