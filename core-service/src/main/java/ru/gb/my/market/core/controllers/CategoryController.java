package ru.gb.my.market.core.controllers;

import ru.gb.my.market.api.dtos.CategoryDto;
import ru.gb.my.market.core.converters.CategoryConverter;
import ru.gb.my.market.core.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll().stream()
                .map(categoryConverter::entityToDto).collect(Collectors.toList());
    }
}
