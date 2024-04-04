package gb.mystore.converters;

import gb.mystore.dtos.CategoryDto;
import gb.mystore.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDto entityToDto(Category category) {
        return new CategoryDto(category.getId(), category.getTitle());
    }
}
