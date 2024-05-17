package ru.gb.my.market.auth.controllers;

import ru.gb.my.market.api.dtos.NewUserDto;
import ru.gb.my.market.api.dtos.UserDto;
import ru.gb.my.market.auth.converters.UserConverter;
import ru.gb.my.market.auth.entities.User;
import ru.gb.my.market.auth.exceptions.ResourceNotFoundException;
import ru.gb.my.market.auth.services.UserService;
import ru.gb.my.market.auth.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;
    private final UserValidator userValidator;

    @PostMapping
    public UserDto addNewUser(@RequestBody NewUserDto newUserDto) {
        userValidator.validate(newUserDto);
        User user = userConverter.newUserDtoToEntity(newUserDto);
        return userConverter.entityToDto(userService.saveNewUser(user));
    }

// Метод ниже не используется.

    @GetMapping
    public String getUserByUsername(@RequestHeader String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getUsername();
        }
        throw new ResourceNotFoundException(
                String.format("User not found. username: %s", username));
    }
}
