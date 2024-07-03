package ru.gb.my.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.gb.my.market.api.OrderDto;
import ru.gb.my.market.api.OrderDtoWithoutItems;
import ru.gb.my.market.api.ProductDto;
import ru.gb.my.market.core.converters.OrderConverter;
import ru.gb.my.market.core.entities.Order;
import ru.gb.my.market.core.exceptions.AppError;
import ru.gb.my.market.core.integrations.CartServiceIntegration;
import ru.gb.my.market.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.my.market.core.validators.OrderValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Заказы", description = "Методы работы с заказами")
// http://localhost:8190/my-market-core/swagger-ui/index.html
public class OrderController {
    private final OrderService orderService;
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderConverter orderConverter;
    private final OrderValidator orderValidator;

    @PostMapping("/create/{cartId}")
    @Operation(summary = "Запрос на создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            })
    public OrderDto createNewOrder(@RequestHeader @Parameter(
            description = "Логин покупателя", required = true) String username,
                                   @PathVariable @Parameter(
            description = "ID корзины", required = true) String cartId,
                                   @RequestBody @Parameter(
            description = "Данные создаваемого заказа", required = true) OrderDtoWithoutItems orderDto) {
        orderValidator.validate(orderDto);
        Order order = orderService.save(username, orderDto.getPhone(),
                orderDto.getAddress(), cartServiceIntegration.getCart(username, cartId));
        cartServiceIntegration.clearCart(username);
        return orderConverter.getOrderDtoFromOrderEntity(order);
    }

    @GetMapping
    @Operation(summary = "Запрос на получение списка заказов данного пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            })
    public List<OrderDtoWithoutItems> getOrders(@RequestHeader @Parameter(
            description = "Логин покупателя", required = true) String username) {
        return orderService.findByUserName(username).stream()
                .map(orderConverter::getOrderDtoWithoutItems)
                .collect(Collectors.toList());
    }

    @DeleteMapping
    @Operation(summary = "Удаление заказа по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            })
    public void removeOrder(@RequestParam(name = "order_id") Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
