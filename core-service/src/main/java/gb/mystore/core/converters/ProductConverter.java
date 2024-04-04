package gb.mystore.core.converters;

import gb.mystore.core.entities.Category;
import gb.mystore.core.entities.Product;
import gb.mystore.core.dtos.ProductDto;
import gb.mystore.core.exceptions.ResourceNotFoundException;
import gb.mystore.core.services.CategoryService;
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
