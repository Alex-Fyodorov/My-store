package gb.mystore.core.converters;

import gb.mystore.core.dtos.CartDto;
import gb.mystore.core.dtos.CartItemDto;
import gb.mystore.core.entities.Cart;
import gb.mystore.core.entities.CartItem;
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
