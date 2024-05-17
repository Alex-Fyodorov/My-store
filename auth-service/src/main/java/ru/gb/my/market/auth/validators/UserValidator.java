package ru.gb.my.market.auth.validators;

import ru.gb.my.market.api.dtos.NewUserDto;
import ru.gb.my.market.auth.services.UserService;
import ru.gb.my.market.auth.entities.User;
import ru.gb.my.market.auth.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserService userService;

    public void validate(NewUserDto newUserDto) {
        List<String> errorMessages = new ArrayList<>();
        Optional<User> user = userService.findByUsername(newUserDto.getUsername());
        if (user.isPresent()) {
            errorMessages.add(String.format("A user %s is already exists.", newUserDto.getUsername()));
        }
        if (newUserDto.getUsername() == null || newUserDto.getUsername().isBlank()) {
            errorMessages.add("The username field is not filled in.");
        }
        if (newUserDto.getPassword() == null || newUserDto.getPassword().isBlank()) {
            errorMessages.add("The password field is not filled in.");
        }
        if (newUserDto.getEmail() == null || newUserDto.getEmail().isBlank()) {
            errorMessages.add("The email field is not filled in.");
        }
        if (!errorMessages.isEmpty()) {
            throw new ValidationException(errorMessages);
        }
    }
}
