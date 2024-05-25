package ru.gb.my.market.core.services;

import ru.gb.my.market.api.CartDto;
import ru.gb.my.market.api.CartItemDto;
import ru.gb.my.market.core.entities.Order;
import ru.gb.my.market.core.entities.OrderItem;
import ru.gb.my.market.core.entities.Product;
//import gb.mystore.core.entities.User;
import ru.gb.my.market.core.exceptions.ResourceNotFoundException;
import ru.gb.my.market.core.integrations.UserServiceIntegration;
import ru.gb.my.market.core.repositories.OrderRepository;
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
    private final ProductService productService;
    private final UserServiceIntegration userServiceIntegration;

    public List<Order> findByUserName(String username) {
        return orderRepository.findByUserName(username);
    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Order save(String username, String phone, String address, CartDto cartDto) {
        if (cartDto.getItems().isEmpty()) throw new IllegalStateException("Cart is empty.");
        Order order = new Order();

        order.setUsername(userServiceIntegration.getUserByUsername(username));
        order.setPhone(phone);
        order.setAddress(address);
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
