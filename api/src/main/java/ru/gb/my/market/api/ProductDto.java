package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель продукта")
public class ProductDto {

    @Schema(description = "ID продукта", example = "1")
    private Long id;
    @Schema(description = "Название продукта", example = "Яблоки", maxLength = 255, minLength = 3)
    private String title;
    @Schema(description = "Цена продукта", example = "120.00", minimum = "1")
    private BigDecimal price;
    @Schema(description = "Категория продукта", example = "Фрукты")
    private String categoryTitle;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
