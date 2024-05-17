package ru.gb.my.market.cart.services;

import ru.gb.my.market.api.dtos.ProductDto;
import ru.gb.my.market.cart.integrations.ProductServiceIntegration;
import ru.gb.my.market.cart.utils.Cart;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addProductToCart(Long productId, Integer delta) {
        ProductDto product = productService.findById(productId);
        cart.changeQuantity(product, delta);
    }

    public void deleteProduct(Long productId) {
        cart.deleteProductFromCart(productId);
    }

    public void clearCart() {
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }
}
