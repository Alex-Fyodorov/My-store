package ru.gb.my.market.api.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItemDto {

    private Long id;
    private Long orderId;
    private Long productId;
    private String productTitle;
    private BigDecimal pricePerProduct;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemDto() {
    }

    public OrderItemDto(Long id, Long orderId, Long productId, String productTitle, BigDecimal pricePerProduct, Integer quantity, BigDecimal price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.pricePerProduct = pricePerProduct;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
