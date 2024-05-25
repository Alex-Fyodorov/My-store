package ru.gb.my.market.core.converters;

import ru.gb.my.market.core.entities.Category;
import ru.gb.my.market.core.entities.Product;
import ru.gb.my.market.api.ProductDto;
import ru.gb.my.market.core.exceptions.ResourceNotFoundException;
import ru.gb.my.market.core.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public ProductDto EntutyToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(),
                product.getPrice(), product.getCategory().getTitle());
    }

    public Product DtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Category %s not found.", productDto.getCategoryTitle())));
        product.setCategory(category);
        return product;
    }
}
