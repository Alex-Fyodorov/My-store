package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель компонента, из которыз состоит содержание корзины")
public class CartItemDto {

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

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle,
                       BigDecimal pricePerProduct, Integer quantity,
                       BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.pricePerProduct = pricePerProduct;
        this.quantity = quantity;
        this.price = price;
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
