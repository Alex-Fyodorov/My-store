package gb.mystore.cart.converters;

import gb.mystore.api.dtos.CartDto;
import gb.mystore.api.dtos.CartItemDto;
import gb.mystore.cart.utils.Cart;
import gb.mystore.cart.utils.CartItem;
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
