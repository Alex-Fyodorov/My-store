package ru.gb.my.market.cart.converters;

import ru.gb.my.market.api.dtos.CartDto;
import ru.gb.my.market.api.dtos.CartItemDto;
import ru.gb.my.market.cart.utils.Cart;
import ru.gb.my.market.cart.utils.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartConverter {

    public CartItemDto cartItemEntityToCartItemDto(CartItem cartItem) {
        return new CartItemDto(cartItem.getProductId(),
                cartItem.getProductTitle(), cartItem.getPricePerProduct(),
                cartItem.getQuantity(), cartItem.getPrice());
    }

    public CartDto cartEntityToCartDto(Cart cart) {
        return new CartDto(cart.getItems().stream().map(this::cartItemEntityToCartItemDto)
                .collect(Collectors.toList()), cart.getTotalPrice());
    }
}
