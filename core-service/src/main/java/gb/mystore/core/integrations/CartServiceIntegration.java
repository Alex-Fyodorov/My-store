package gb.mystore.core.integrations;

import gb.mystore.api.dtos.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public CartDto getCart() {
        return restTemplate.getForObject(
                "http://localhost:8191/cart/api/v1/current-cart", CartDto.class);
    }

    public void clearCart() {
        restTemplate.delete("http://localhost:8191/cart/api/v1/current-cart/clear");
    }
}
