package gb.mystore.controllers;

import gb.mystore.converters.CartConverter;
import gb.mystore.dtos.CartDto;
import gb.mystore.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.cartEntityToCartDto(cartService.getCurrentCart());
    }

    @GetMapping("/add")
    public void addProductToCart(@RequestParam (name = "product_id") Long productId,
                                 @RequestParam (defaultValue = "1") Integer delta) {
        cartService.addProductToCart(productId, delta);
    }

    @GetMapping("/delete/{productId}")
    public void deleteProductFromCart(@PathVariable Long productId) {
        cartService.deleteProduct(productId);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}
