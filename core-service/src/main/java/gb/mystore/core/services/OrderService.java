package gb.mystore.core.services;

import gb.mystore.api.dtos.CartDto;
import gb.mystore.api.dtos.CartItemDto;
import gb.mystore.core.entities.Order;
import gb.mystore.core.entities.OrderItem;
import gb.mystore.core.entities.Product;
//import gb.mystore.core.entities.User;
import gb.mystore.core.exceptions.ResourceNotFoundException;
import gb.mystore.core.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    //private final UserService userService;
    private final ProductService productService;

    public List<Order> findByUserName(String username) {
        return orderRepository.findByUserName(username);
    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Order save(String username, CartDto cartDto) {
        if (cartDto.getItems().isEmpty()) throw new IllegalStateException("Cart is empty.");
        Order order = new Order();
//        User user = userService.findByUsername(username).orElseThrow(() ->
//                new UsernameNotFoundException(String.format(
//                        "User %s not found.", username)));
        //order.setUser(user);
        order.setUsername("user");
        order.setTotalPrice(cartDto.getTotalPrice());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemDto item : cartDto.getItems()) {
            OrderItem orderItem = new OrderItem();
            Product product = productService.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Product not found. id: %d", item.getProductId())));
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPricePerProduct(item.getPricePerProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
