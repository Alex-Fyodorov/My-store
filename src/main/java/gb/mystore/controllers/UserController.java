package gb.mystore.controllers;

import gb.mystore.converters.UserConverter;
import gb.mystore.dtos.NewUserDto;
import gb.mystore.dtos.UserDto;
import gb.mystore.entities.User;
import gb.mystore.services.UserService;
import gb.mystore.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
