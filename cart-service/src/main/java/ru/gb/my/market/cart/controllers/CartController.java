package ru.gb.my.market.cart.controllers;

import ru.gb.my.market.api.CartDto;
import ru.gb.my.market.api.StringResponse;
import ru.gb.my.market.cart.converters.CartConverter;
import ru.gb.my.market.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/current-cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{cartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username,
                                  @PathVariable String cartId) {
        String cartKey = cartService.selectCartId(username, cartId);
        return cartConverter.cartEntityToCartDto(cartService.getCurrentCart(cartKey));
    }

    @GetMapping("/{cartId}/add")
    public void addProductToCart(@RequestHeader(required = false) String username,
                                 @PathVariable String cartId,
                                 @RequestParam (name = "product_id") Long productId,
                                 @RequestParam (defaultValue = "1") Integer delta) {
        String cartKey = cartService.selectCartId(username, cartId);
        cartService.addProductToCart(cartKey, productId, delta);
    }

    @GetMapping("/{cartId}/delete/{productId}")
    public void deleteProductFromCart(@RequestHeader(required = false) String username,
                                      @PathVariable String cartId,
                                      @PathVariable Long productId) {
        String cartKey = cartService.selectCartId(username, cartId);
        cartService.deleteProduct(cartKey, productId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@RequestHeader(required = false) String username) {
        cartService.clearCart(username);
    }
}
