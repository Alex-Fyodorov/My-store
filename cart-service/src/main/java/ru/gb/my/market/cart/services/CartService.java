package ru.gb.my.market.cart.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.my.market.api.ProductDto;
import ru.gb.my.market.cart.integrations.ProductServiceIntegration;
import ru.gb.my.market.cart.utils.Cart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private Map<String, Cart> carts;
    @Value("${user.default}")
    private String defaultUsername;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String username) {
        username = checkUsername(username);
        return carts.get(username);
    }

    public void addProductToCart(String username, Long productId, Integer delta) {
        username = checkUsername(username);
        ProductDto product = productService.findById(productId);
        carts.get(username).changeQuantity(product, delta);
    }

    public void deleteProduct(String username, Long productId) {
        username = checkUsername(username);
        carts.get(username).deleteProductFromCart(productId);
    }

    public void clearCart(String username) {
        username = checkUsername(username);
        Cart cart = carts.get(username);
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }

    private String checkUsername(String username) {
        if (username == null) {
            username = defaultUsername;
        }
        if (!carts.containsKey(username)) {
            Cart cart = new Cart();
            carts.put(username, cart);
        }
        if (!username.equals(defaultUsername) && carts.containsKey(defaultUsername)) {
            Cart cartUser = carts.get(username);
            carts.get(defaultUsername).getItems().forEach(i -> {
                ProductDto productDto = new ProductDto(i.getProductId(),
                        i.getProductTitle(), i.getPricePerProduct(), null);
                cartUser.changeQuantity(productDto, i.getQuantity());
            });
            carts.remove(defaultUsername);
        }
        return username;
    }
}
