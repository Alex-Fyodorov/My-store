package gb.mystore.core.validators;

import gb.mystore.core.exceptions.ValidationException;
import gb.mystore.core.dtos.ProductDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto) throws ValidationException {
        List<String> messages = new ArrayList<>();
        if (productDto.getTitle() == null || productDto.getTitle().isBlank()) {
            messages.add("The title field is not filled in.");
        }
        if (productDto.getPrice() == null || productDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            messages.add("The price is missing or less than zero.");
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
        if (productDto.getCategoryTitle() == null || productDto.getCategoryTitle().isBlank()) {
            messages.add("The category field is not filled in.");
        }
    }
}
