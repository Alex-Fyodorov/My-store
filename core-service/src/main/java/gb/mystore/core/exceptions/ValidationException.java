package gb.mystore.core.exceptions;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<String> errorFieldsMessages;

    public ValidationException(List<String> errorFieldsMessages) {
        super(String.join("; ", errorFieldsMessages));
        this.errorFieldsMessages = errorFieldsMessages;
    }
}
