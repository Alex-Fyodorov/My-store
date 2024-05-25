package ru.gb.my.market.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDtoWithoutItems {

    private Long id;
    private String username;
    private String phone;
    private String address;
    private BigDecimal totalPrice;
    private String createdAt;

    public OrderDtoWithoutItems() {
    }

    public OrderDtoWithoutItems(Long id, String username, String phone,
                                String address, BigDecimal totalPrice,
                                String createdAt) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.totalPrice = totalPrice;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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
