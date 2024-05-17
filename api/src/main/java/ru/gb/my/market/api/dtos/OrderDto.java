package ru.gb.my.market.api.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDto {

    private Long id;
    private String username;
    private BigDecimal totalPrice;
    private List<OrderItemDto> orderItems;
    private Date createdAt;

    public OrderDto() {
    }

    public OrderDto(Long id, String username, BigDecimal totalPrice,
                    List<OrderItemDto> orderItems, Date createdAt) {
        this.id = id;
        this.username = username;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
