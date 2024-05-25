package ru.gb.my.market.core.validators;

import org.springframework.stereotype.Component;
import ru.gb.my.market.api.OrderDtoWithoutItems;
import ru.gb.my.market.core.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderValidator {

    public void validate(OrderDtoWithoutItems orderDto) {
        List<String> errorMessages = new ArrayList<>();
        if (orderDto.getPhone() == null || orderDto.getPhone().isBlank()) {
            errorMessages.add("The phone field is not filled in.");
        }
        if (orderDto.getAddress() == null || orderDto.getAddress().isBlank()) {
            errorMessages.add("The address field is not filled in.");
        }
        if (!errorMessages.isEmpty()) {
            throw new ValidationException(errorMessages);
        }
    }
}
