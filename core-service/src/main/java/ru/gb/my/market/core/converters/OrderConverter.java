package ru.gb.my.market.core.converters;

import ru.gb.my.market.api.OrderDto;
import ru.gb.my.market.api.OrderDtoWithoutItems;
import ru.gb.my.market.api.OrderItemDto;
import ru.gb.my.market.core.entities.Order;
import ru.gb.my.market.core.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public OrderDto getOrderDtoFromOrderEntity(Order order) {
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(this::getItemDtoFromItemEntity).collect(Collectors.toList());
        return new OrderDto(order.getId(),
                //order.getUser().getUsername(),
                order.getUsername(),
                order.getPhone(),
                order.getAddress(),
                order.getTotalPrice(),
                orderItemDtos,
                order.getCreatedAt());
    }

    public OrderItemDto getItemDtoFromItemEntity(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getTitle(),
                orderItem.getPricePerProduct(),
                orderItem.getQuantity(),
                orderItem.getPrice());
    }

    public OrderDtoWithoutItems getOrderDtoWithoutItems(Order order) {
        Date date = order.getCreatedAt();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return new OrderDtoWithoutItems(order.getId(),
                //order.getUser().getUsername(),
                order.getUsername(),
                order.getPhone(),
                order.getAddress(),
                order.getTotalPrice(),
                format.format(date));
    }
}
