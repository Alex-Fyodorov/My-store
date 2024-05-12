package gb.mystore.core.controllers;

import gb.mystore.api.dtos.NewUserDto;
import gb.mystore.api.dtos.UserDto;
import gb.mystore.core.converters.UserConverter;
import gb.mystore.core.entities.User;
import gb.mystore.core.services.UserService;
import gb.mystore.core.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
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
}
