package gb.mystore.core.services;

import gb.mystore.core.entities.Category;
import gb.mystore.core.exceptions.ResourceNotFoundException;
import gb.mystore.core.dtos.ProductDto;
import gb.mystore.core.entities.Product;
import gb.mystore.core.repositories.ProductRepository;
import gb.mystore.core.repositories.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> findAll(BigDecimal minPrice, BigDecimal maxPrice,
                                 String titlePart, Long categoryId, Integer page, String sort) {
        Specification<Product> specification = Specification.where(null);
        if (minPrice != null) {
            specification = specification.and(ProductSpecification.priceGreaterOrEqualThan(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductSpecification.priceLessOrEqualThan(maxPrice));
        }
        if (titlePart != null) {
            specification = specification.and(ProductSpecification.likeOf(titlePart));
        }
        if (categoryId != null) {
            Category category = categoryService.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Category not found. id: %d", categoryId)));
            specification = specification.and(ProductSpecification.categoryEqualThan(category));
        }
        if (page < 1) page = 1;
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5, Sort.by(sort)));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(ProductDto productDto) {
        Product product = findById(productDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product not found. id: %d", productDto.getId())));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Category %s not found.", productDto.getCategoryTitle())));
        product.setCategory(category);
        return product;
    }

    @Transactional
    public Optional<Product> deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) productRepository.deleteById(id);
        return product;
    }

    public void changePrice(Long id, BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) newPrice = BigDecimal.ZERO;
        productRepository.changePrice(id, newPrice);
    }
}
