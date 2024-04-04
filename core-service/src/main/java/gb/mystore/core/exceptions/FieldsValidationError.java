package gb.mystore.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldsValidationError {
    private int statusCode;
    private List<String> errorFieldsMessages;
}