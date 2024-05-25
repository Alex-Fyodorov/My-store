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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private Map<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String cartId) {
        checkCartId(cartId);
        return carts.get(cartId);
    }

    public void addProductToCart(String cartId, Long productId, Integer delta) {
        checkCartId(cartId);
        ProductDto product = productService.findById(productId);
        carts.get(cartId).changeQuantity(product, delta);
    }

    public void deleteProduct(String cartId, Long productId) {
        carts.get(cartId).deleteProductFromCart(productId);
    }

    public void clearCart(String cartId) {
        Cart cart = carts.get(cartId);
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }

    private void checkCartId(String cartId) {
        if (!carts.containsKey(cartId)) {
            carts.put(cartId, new Cart());
        }
    }

    public String selectCartId(String username, String cartId) {
        if (username == null) {
            return cartId;
        }
        if (!carts.get(cartId).getItems().isEmpty()) {
            checkCartId(username);
            Cart cartUser = carts.get(username);
            carts.get(cartId).getItems().forEach(i -> {
                ProductDto productDto = new ProductDto(i.getProductId(),
                        i.getProductTitle(), i.getPricePerProduct(), null);
                cartUser.changeQuantity(productDto, i.getQuantity());
            });
            clearCart(cartId);
        }
        return username;
    }
}
