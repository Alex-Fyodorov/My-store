package ru.gb.my.market.cart.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.my.market.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
    }

    public void changeQuantity(ProductDto product, Integer delta) {
        for (CartItem item : items) {
            if (item.getProductId().equals(product.getId())) {
                item.changeQuantity(delta);
                if (item.getQuantity() <= 0) items.remove(item);
                recalculate();
                return;
            }
        }
        CartItem cartItem = new CartItem(product.getId(), product.getTitle(),
                product.getPrice(), 0, BigDecimal.ZERO);
        cartItem.changeQuantity(delta);
        if (cartItem.getQuantity() > 0) {
            items.add(cartItem);
        }
        recalculate();
    }

    public void deleteProductFromCart(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        recalculate();
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        items.forEach(item -> totalPrice = totalPrice.add(item.getPrice()));
    }
}
