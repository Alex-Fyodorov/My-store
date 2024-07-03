package ru.gb.my.market.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.gb.my.market.api.NewUserDto;
import ru.gb.my.market.api.ProductDto;
import ru.gb.my.market.api.UserDto;
import ru.gb.my.market.auth.converters.UserConverter;
import ru.gb.my.market.auth.entities.User;
import ru.gb.my.market.auth.exceptions.AppError;
import ru.gb.my.market.auth.exceptions.ResourceNotFoundException;
import ru.gb.my.market.auth.services.UserService;
import ru.gb.my.market.auth.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Пользователи", description = "Методы работы с пользователями")
// http://localhost:8187/my-market-auth/swagger-ui/index.html
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;
    private final UserValidator userValidator;

    @PostMapping
    @Operation(summary = "Запрос на создание нового пользователя",
            responses = {
                    @ApiResponse(
                            description = "Пользователь успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    )
            })
    public UserDto addNewUser(@RequestBody NewUserDto newUserDto) {
        userValidator.validate(newUserDto);
        User user = userConverter.newUserDtoToEntity(newUserDto);
        return userConverter.entityToDto(userService.saveNewUser(user));
    }

    @GetMapping
    @Operation(summary = "Запрос на получение пользователя по имени",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            })
    public String getUserByUsername(@RequestHeader String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getUsername();
        }
        throw new ResourceNotFoundException(
                String.format("User not found. username: %s", username));
    }
}
