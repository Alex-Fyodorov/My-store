package gb.mystore.core.controllers;

import gb.mystore.api.dtos.OrderDto;
import gb.mystore.api.dtos.OrderDtoWithoutItems;
import gb.mystore.core.converters.OrderConverter;
import gb.mystore.core.entities.Order;
import gb.mystore.core.integrations.CartServiceIntegration;
import gb.mystore.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public OrderDto createNewOrder(Principal principal) {
        Order order = orderService.save(principal.getName(), cartServiceIntegration.getCart());
        cartServiceIntegration.clearCart();
        return orderConverter.getOrderDtoFromOrderEntity(order);
    }

    @GetMapping
    public List<OrderDtoWithoutItems> getOrders(Principal principal) {
        return orderService.findByUserName(principal.getName()).stream()
                .map(orderConverter::getOrderDtoWithoutItems)
                .collect(Collectors.toList());
    }

    @DeleteMapping
    public void removeOrder(@RequestParam(name = "order_id") Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
