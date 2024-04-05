package gb.mystore.core.converters;

import gb.mystore.api.dtos.CategoryDto;
import gb.mystore.core.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDto entityToDto(Category category) {
        return new CategoryDto(category.getId(), category.getTitle());
    }
}
