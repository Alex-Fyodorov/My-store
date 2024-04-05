package gb.mystore.core.converters;

import gb.mystore.api.dtos.NewUserDto;
import gb.mystore.api.dtos.UserDto;
import gb.mystore.core.entities.Role;
import gb.mystore.core.entities.User;
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
