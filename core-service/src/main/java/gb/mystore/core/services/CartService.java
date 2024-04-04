package gb.mystore.core.services;

import gb.mystore.core.entities.Cart;
import gb.mystore.core.entities.Product;
import gb.mystore.core.exceptions.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
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
        Product product = productService.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found. id: %d", productId)));
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
