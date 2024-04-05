package gb.mystore.cart.services;

import gb.mystore.api.dtos.ProductDto;
import gb.mystore.cart.integrations.ProductServiceIntegration;
import gb.mystore.cart.utils.Cart;
import gb.mystore.cart.exceptions.ResourceNotFoundException;
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
