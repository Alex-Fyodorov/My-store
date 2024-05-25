package ru.gb.my.market.cart.controllers;

import ru.gb.my.market.api.CartDto;
import ru.gb.my.market.cart.converters.CartConverter;
import ru.gb.my.market.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/current-cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart(@RequestHeader(required = false) String username) {
        return cartConverter.cartEntityToCartDto(cartService.getCurrentCart(username));
    }

    @GetMapping("/add")
    public void addProductToCart(@RequestHeader(required = false) String username,
                                 @RequestParam (name = "product_id") Long productId,
                                 @RequestParam (defaultValue = "1") Integer delta) {
        cartService.addProductToCart(username, productId, delta);
    }

    @GetMapping("/delete/{productId}")
    public void deleteProductFromCart(@RequestHeader(required = false) String username,
                                      @PathVariable Long productId) {
        cartService.deleteProduct(username, productId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@RequestHeader(required = false) String username) {
        cartService.clearCart(username);
    }

//    private String checkUsername(String username) {
//        if (username == null) return "#DEFAULT USER#";
//        else return username;
//    }
}
