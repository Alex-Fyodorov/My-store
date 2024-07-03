package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи токена")
public class JwtResponse {

    @Schema(description = "Токен", example = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidXNlcjEiLCJpYXQiOj" +
            "E3MjAwMDczMDksImV4cCI6MTcyMDAxMDkwOX0.JCUxbEYcsYPar_" +
            "8TTQDox4V8VQhHGWDsXRspX0qDOg4")
    private String token;

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
