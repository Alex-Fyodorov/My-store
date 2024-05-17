package ru.gb.my.market.cart.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private BigDecimal pricePerProduct;
    private Integer quantity;
    private BigDecimal price;

    public void changeQuantity(Integer delta) {
        quantity += delta;
        price = BigDecimal.valueOf(quantity).multiply(pricePerProduct);
    }
}
