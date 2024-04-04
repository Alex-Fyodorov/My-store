package gb.mystore.core.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUserDto {
    private String username;
    private String password;
    private String email;
}
