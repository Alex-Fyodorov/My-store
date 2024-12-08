package ru.gb.my.market.core;

import ru.gb.my.market.core.entities.Category;
import ru.gb.my.market.core.entities.Product;
import ru.gb.my.market.core.repositories.ProductRepository;
import ru.gb.my.market.core.services.CategoryService;
import ru.gb.my.market.core.services.ProductService;
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

// Верификация: метод был вызван с заданными параметрами
//    Mockito.verify(calculatorService).printSum(10.0, 20.0);

// Верификация: был ли вызван метод placeOrder с testOrder
//    Mockito.verify(orderService).placeOrder(testOrder);

// Верификация: метод placeOrder был вызван дважды
//    Mockito.verify(orderService, Mockito.times(2)).placeOrder(Mockito.any(Order.class));

// Верификация: метод getOrderCount ни разу не был вызван
//    Mockito.verify(orderService, Mockito.never()).getOrderCount();

// Верификация: был ли метод вызван с определенными аргументами
//    Mockito.verify(mockUserService).findUser(Mockito.eq("Alice"), Mockito.eq(30));

// Верификация вызова метода
//    Mockito.verify(spyListManager).getListSize(list);