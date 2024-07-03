package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель нового пользователя")
public class NewUserDto {

    @Schema(description = "Имя пользователя", example = "Сато Кейко")
    private String username;
    @Schema(description = "Пароль пользователя", example = "sdfg65446fg23")
    private String password;
    @Schema(description = "Email пользователя", example = "user@info.com")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
