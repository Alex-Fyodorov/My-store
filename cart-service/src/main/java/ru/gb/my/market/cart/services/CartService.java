package ru.gb.my.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.my.market.api.ProductDto;
import ru.gb.my.market.cart.integrations.ProductServiceIntegration;
import ru.gb.my.market.cart.utils.Cart;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private final RedisTemplate<String, Object> redisTemplate;

    public Cart getCurrentCart(String cartId) {
        checkCartId(cartId);
        return (Cart) redisTemplate.opsForValue().get(cartId);
    }

    public void addProductToCart(String cartId, Long productId, Integer delta) {
        checkCartId(cartId);
        execute(cartId, cart -> {
            ProductDto product = productService.findById(productId);
            cart.changeQuantity(product, delta);
        });
    }

    public void deleteProduct(String cartId, Long productId) {
        execute(cartId, cart -> cart.deleteProductFromCart(productId));
    }

    public void clearCart(String cartId) {
        execute(cartId, cart -> {
            cart.getItems().clear();
            cart.setTotalPrice(BigDecimal.ZERO);
        });
    }

    private void checkCartId(String cartId) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(cartId))) {
            redisTemplate.opsForValue().set(cartId, new Cart());
        }
    }

    public String selectCartId(String username, String cartId) {
        if (username == null) {
            return cartId;
        }
        Cart cart = (Cart) redisTemplate.opsForValue().get(cartId);
        if (cart != null && !cart.getItems().isEmpty()) {
            mergeCart(username, cartId);
        }
        return username;
    }

    private void mergeCart(String username, String cartId) {
        checkCartId(username);
        Cart cartUser = (Cart) redisTemplate.opsForValue().get(username);
        Cart cartByCartId = (Cart) redisTemplate.opsForValue().get(cartId);
        if (cartUser != null && cartByCartId != null) {
            cartByCartId.getItems().forEach(i -> {
                ProductDto productDto = new ProductDto(i.getProductId(),
                        i.getProductTitle(), i.getPricePerProduct(), null);
                cartUser.changeQuantity(productDto, i.getQuantity());
            });
            redisTemplate.opsForValue().set(username, cartUser);
            clearCart(cartId);
        }
    }

    private void execute(String cartId, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartId);
        if (cart != null) {
            action.accept(cart);
            redisTemplate.opsForValue().set(cartId, cart);
        }
    }
}
