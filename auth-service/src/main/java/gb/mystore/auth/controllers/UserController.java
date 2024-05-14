package gb.mystore.auth.controllers;

import gb.mystore.api.dtos.NewUserDto;
import gb.mystore.api.dtos.UserDto;
import gb.mystore.auth.converters.UserConverter;
import gb.mystore.auth.entities.User;
import gb.mystore.auth.exceptions.ResourceNotFoundException;
import gb.mystore.auth.services.UserService;
import gb.mystore.auth.validators.UserValidator;
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
