package ru.gb.my.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи ID корзины")
public class StringResponse {

    @Schema(description = "ID корзины в формате UUID", example = "44c80e14-3b1f-40fc-a0e9-a81ef1c784ab")
    private String value;

    public StringResponse() {
    }

    public StringResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
