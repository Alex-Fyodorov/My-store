package ru.gb.my.market.core.converters;

import ru.gb.my.market.api.CategoryDto;
import ru.gb.my.market.core.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDto entityToDto(Category category) {
        return new CategoryDto(category.getId(), category.getTitle());
    }
}
