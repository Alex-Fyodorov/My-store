package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Schema(description = "Модель заказа")
public class OrderDto {

    @Schema(description = "ID заказа", example = "1")
    private Long id;
    @Schema(description = "Логин покупателя", example = "Сато Кейко")
    private String username;
    @Schema(description = "Телефон покупателя", example = "681-1268")
    private String phone;
    @Schema(description = "Адрес покупателя", example = "ул. 3-го Интернационала, д.57, кв.166")
    private String address;
    @Schema(description = "Цена заказа", example = "365.00")
    private BigDecimal totalPrice;
    @Schema(description = "Список компонентов заказа")
    private List<OrderItemDto> orderItems;
    @Schema(description = "Дата и время создания заказа", example = "2023-07-25, 15:54")
    private Date createdAt;

    public OrderDto() {
    }

    public OrderDto(Long id, String username, String phone, String address,
                    BigDecimal totalPrice, List<OrderItemDto> orderItems,
                    Date createdAt) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
