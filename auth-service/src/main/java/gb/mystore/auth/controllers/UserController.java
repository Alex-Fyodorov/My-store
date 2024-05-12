package gb.mystore.auth.controllers;

import gb.mystore.api.dtos.NewUserDto;
import gb.mystore.api.dtos.UserDto;
import gb.mystore.auth.converters.UserConverter;
import gb.mystore.auth.entities.User;
import gb.mystore.auth.services.UserService;
import gb.mystore.auth.validators.UserValidator;
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
