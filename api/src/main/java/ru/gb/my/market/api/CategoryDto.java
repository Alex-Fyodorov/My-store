package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель категории продукта")
public class CategoryDto {

    @Schema(description = "ID категории", example = "1")
    private Long id;
    @Schema(description = "Название категории", example = "Фрукты", maxLength = 255, minLength = 3)
    private String title;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String title) {
        this.id = id;
        this.title = title;
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
}
