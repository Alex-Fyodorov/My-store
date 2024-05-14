package gb.mystore.core.controllers;

import gb.mystore.api.dtos.ProductDto;
import gb.mystore.core.converters.ProductConverter;
import gb.mystore.core.exceptions.ResourceNotFoundException;
import gb.mystore.core.entities.Product;

import gb.mystore.core.services.ProductService;
import gb.mystore.core.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
//@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping
    public Page<ProductDto> findAll(
            @RequestParam(required = false, name = "min_price") BigDecimal min,
            @RequestParam(required = false, name = "max_price") BigDecimal max,
            @RequestParam(required = false, name = "title_part") String titlePart,
            @RequestParam(required = false, name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "id") String sort) {
        return productService.findAll(min, max, titlePart, categoryId, page, sort)
                .map(productConverter::EntutyToDto);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id).map(productConverter::EntutyToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found. id: %d", id)));
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.DtoToEntity(productDto);
        return productConverter.EntutyToDto(productService.saveProduct(product));
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.updateProduct(productDto);
        return productConverter.EntutyToDto(product);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteById(@PathVariable Long id) {
        return productService.deleteById(id).map(productConverter::EntutyToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found. id: %d", id)));

    }

    @PatchMapping
    public void changePrice(@RequestParam(name = "product_id") Long id,
                            @RequestParam(name = "new_price") BigDecimal newPrice) {
        productService.changePrice(id, newPrice);
    }
}
