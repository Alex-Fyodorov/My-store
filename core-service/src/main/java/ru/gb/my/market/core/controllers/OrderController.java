package ru.gb.my.market.core.controllers;

import ru.gb.my.market.api.dtos.OrderDto;
import ru.gb.my.market.api.dtos.OrderDtoWithoutItems;
import ru.gb.my.market.core.converters.OrderConverter;
import ru.gb.my.market.core.entities.Order;
import ru.gb.my.market.core.integrations.CartServiceIntegration;
import ru.gb.my.market.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderConverter orderConverter;

    @GetMapping("/create")
    public OrderDto createNewOrder(@RequestHeader String username) {
        Order order = orderService.save(username, cartServiceIntegration.getCart());
        cartServiceIntegration.clearCart();
        return orderConverter.getOrderDtoFromOrderEntity(order);
    }

    @GetMapping
    public List<OrderDtoWithoutItems> getOrders(@RequestHeader String username) {
        return orderService.findByUserName(username).stream()
                .map(orderConverter::getOrderDtoWithoutItems)
                .collect(Collectors.toList());
    }

    @DeleteMapping
    public void removeOrder(@RequestParam(name = "order_id") Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
