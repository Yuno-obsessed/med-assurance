package sanity.nil.medassurance.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sanity.nil.medassurance.dto.ErrorResponseDTO;
import sanity.nil.medassurance.exceptions.DuplicatedUserEntryException;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedUserEntryException.class)
    public ErrorResponseDTO handle(final DuplicatedUserEntryException exception) {
        return new ErrorResponseDTO(exception.getMessage());
    }

}
