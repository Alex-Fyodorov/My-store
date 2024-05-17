package gb.mystore.core;

import gb.mystore.core.entities.Category;
import gb.mystore.core.entities.Product;
import gb.mystore.core.repositories.ProductRepository;
import gb.mystore.core.services.CategoryService;
import gb.mystore.core.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
//@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryService categoryService;

    @Test
    public void saveProductTest() {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Dairy");
        category.setProducts(Collections.emptyList());

        Mockito.doReturn(Optional.of(category))
                .when(categoryService)
                .findByTitle("Dairy");

        Product product = new Product();
        product.setTitle("Milk");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory(categoryService.findByTitle("Dairy").get());
        productService.saveProduct(product);

        Mockito.verify(productRepository, Mockito.times(1))
                .save(ArgumentMatchers.any());
    }
}
