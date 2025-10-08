 package br.com.alura.projeto.exception;

import br.com.alura.projeto.util.ErrorItemDTO;
import lombok.AllArgsConstructor;

public class ServiceException extends RuntimeException {

    private final String field;
    private final String message;

    public ServiceException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

    public ErrorItemDTO toDTO() {
        return new ErrorItemDTO(field, message);
    }

}
