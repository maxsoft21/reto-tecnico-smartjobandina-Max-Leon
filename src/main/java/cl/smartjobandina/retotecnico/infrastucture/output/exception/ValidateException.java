package cl.smartjobandina.retotecnico.infrastucture.output.exception;


import lombok.NoArgsConstructor;
@NoArgsConstructor
public class ValidateException extends RuntimeException {

    public ValidateException(String message) {
        super(message);
    }
}
