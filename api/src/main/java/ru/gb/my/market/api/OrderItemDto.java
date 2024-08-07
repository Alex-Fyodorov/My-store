package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "Модель компонента, из которых строится общий заказ")
public class OrderItemDto {

    @Schema(description = "ID компонента заказа", example = "1")
    private Long id;
    @Schema(description = "ID заказа, к которому принадлежит данный компонент", example = "1")
    private Long orderId;
    @Schema(description = "ID продукта", example = "1")
    private Long productId;
    @Schema(description = "Название продукта", example = "Яблоко")
    private String productTitle;
    @Schema(description = "Цена продукта", example = "120.00")
    private BigDecimal pricePerProduct;
    @Schema(description = "Количество", example = "1")
    private Integer quantity;
    @Schema(description = "Общая цена", example = "120.00")
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
