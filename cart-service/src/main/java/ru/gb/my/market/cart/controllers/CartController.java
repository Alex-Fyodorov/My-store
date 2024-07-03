package ru.gb.my.market.cart.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Корзины", description = "Методы работы с корзинами")
// http://localhost:8191/my-market-cart/swagger-ui/index.html
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_id")
    @Operation(summary = "Запрос на создание ID корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            })
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Запрос на получение корзины по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            })
    public CartDto getCurrentCart(@RequestHeader(required = false) String username,
                                  @PathVariable String cartId) {
        String cartKey = cartService.selectCartId(username, cartId);
        return cartConverter.cartEntityToCartDto(cartService.getCurrentCart(cartKey));
    }

    @GetMapping("/{cartId}/add")
    @Operation(summary = "Запрос на добавление продукта в корзину",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            })
    public void addProductToCart(@RequestHeader(required = false) @Parameter(
            description = "Имя покупателя", required = false) String username,
                                 @PathVariable @Parameter(
            description = "ID корзины", required = true) String cartId,
                                 @RequestParam (name = "product_id") @Parameter(
            description = "ID продукта", required = true) Long productId,
                                 @RequestParam (defaultValue = "1") @Parameter(
            description = "Количество добавляемого продукта", required = false) Integer delta) {
        String cartKey = cartService.selectCartId(username, cartId);
        cartService.addProductToCart(cartKey, productId, delta);
    }

    @GetMapping("/{cartId}/delete/{productId}")
    @Operation(summary = "Запрос на удаление продукта из корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            })
    public void deleteProductFromCart(@RequestHeader(required = false) @Parameter(
            description = "Имя покупателя", required = false) String username,
                                      @PathVariable @Parameter(
            description = "ID корзины", required = true) String cartId,
                                      @PathVariable @Parameter(
            description = "ID продукта", required = true) Long productId) {
        String cartKey = cartService.selectCartId(username, cartId);
        cartService.deleteProduct(cartKey, productId);
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Запрос очистку корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            })
    public void clearCart(@RequestHeader @Parameter(
            description = "Имя покупателя", required = true) String username) {
        cartService.clearCart(username);
    }
}
