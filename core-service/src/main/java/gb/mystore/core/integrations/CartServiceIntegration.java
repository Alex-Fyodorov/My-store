package gb.mystore.core.integrations;

import gb.mystore.api.dtos.CartDto;
import gb.mystore.api.dtos.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

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
        cartServiceWebClient.get()
                .uri("api/v1/current-cart/clear")
                //.header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
