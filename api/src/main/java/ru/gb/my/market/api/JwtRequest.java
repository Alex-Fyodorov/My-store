package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель запроса на получение токена")
public class JwtRequest {

    @Schema(description = "Имя пользователя", example = "Сато Кейко")
    private String username;
    @Schema(description = "Пароль пользователя", example = "sdfg65446fg23")
    private String password;

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
}
