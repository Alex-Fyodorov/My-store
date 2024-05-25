package ru.gb.my.market.auth.converters;

import ru.gb.my.market.api.NewUserDto;
import ru.gb.my.market.api.UserDto;
import ru.gb.my.market.auth.entities.Role;
import ru.gb.my.market.auth.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

    public User newUserDtoToEntity(NewUserDto newUserDto) {
        User user = new User();
        user.setUsername(newUserDto.getUsername());
        user.setPassword(newUserDto.getPassword());
        user.setEmail(newUserDto.getEmail());
        return user;
    }

    public UserDto entityToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }
}
