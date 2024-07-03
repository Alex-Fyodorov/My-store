package ru.gb.my.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.gb.my.market.api.ProductDto;
import ru.gb.my.market.core.converters.ProductConverter;
import ru.gb.my.market.core.exceptions.AppError;
import ru.gb.my.market.core.exceptions.ResourceNotFoundException;
import ru.gb.my.market.core.entities.Product;

import ru.gb.my.market.core.services.ProductService;
import ru.gb.my.market.core.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Продукты", description = "Методы работы с продуктами")
// http://localhost:8190/my-market-core/swagger-ui/index.html
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping
    @Operation(summary = "Запрос на получение страницы с продуктами",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            })
    public Page<ProductDto> findAll(
            @RequestParam(required = false, name = "min_price") @Parameter(
                    description = "Фильтр по минимальной цене", required = false) BigDecimal min,
            @RequestParam(required = false, name = "max_price") @Parameter(
                    description = "Фильтр по максимальной цене", required = false) BigDecimal max,
            @RequestParam(required = false, name = "title_part") @Parameter(
                    description = "Фильтр по части названия продукта", required = false) String titlePart,
            @RequestParam(required = false, name = "category_id") @Parameter(
                    description = "Фильтр по категории", required = false) Long categoryId,
            @RequestParam(defaultValue = "1") @Parameter(description = "Номер страницы",
                    required = false) Integer page,
            @RequestParam(defaultValue = "id") @Parameter(description = "Сортировка продуктов по id, " +
                    "названию или цене", required = false) String sort) {
        return productService.findAll(min, max, titlePart, categoryId, page, sort)
                .map(productConverter::EntutyToDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Запрос на получение продукта по id",
            responses = {
                @ApiResponse(
                        description = "Успешный ответ", responseCode = "200",
                        content = @Content(schema = @Schema(implementation = ProductDto.class))
                ),
                @ApiResponse(
                        description = "Продукт не найден", responseCode = "404",
                        content = @Content(schema = @Schema(implementation = AppError.class))
                )
            })
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id).map(productConverter::EntutyToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found. id: %d", id)));
    }

    @PostMapping
    @Operation(summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            })
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.DtoToEntity(productDto);
        return productConverter.EntutyToDto(productService.saveProduct(product));
    }

    @PutMapping
    @Operation(summary = "Запрос на изменение продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно изменён", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Категория не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            })
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.updateProduct(productDto);
        return productConverter.EntutyToDto(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Запрос на удаление продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно удалён", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            })
    public ProductDto deleteById(@PathVariable Long id) {
        return productService.deleteById(id).map(productConverter::EntutyToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found. id: %d", id)));

    }

    @PatchMapping
    @Operation(summary = "Запрос на изменение цены продукта",
            responses = {
                    @ApiResponse(
                            description = "Цена продукта успешно изменена", responseCode = "200"
                    )
            })
    public void changePrice(@RequestParam(name = "product_id") @Parameter(
            description = "ID продукта", required = true) Long id,
                            @RequestParam(name = "new_price") @Parameter(
            description = "Новая цена", required = true) BigDecimal newPrice) {
        productService.changePrice(id, newPrice);
    }
}
