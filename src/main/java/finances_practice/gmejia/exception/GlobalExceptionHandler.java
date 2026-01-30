package finances_practice.gmejia.exception;

import finances_practice.gmejia.dto.ErrorDto;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar BusinessException (Errores logicos controlados)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<@NonNull ErrorDto> handleBusinessException(BusinessException ex){
        ErrorDto error = ErrorDto.builder()
                .status(ex.getStatus().value()) //Devuelve el valor del error
                .error(ex.getStatus().getReasonPhrase()) //Devuelve el texto del error
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, ex.getStatus());
    }

    // Manejar ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<@NonNull ErrorDto> handleResponseStatusException(ResponseStatusException ex){
        ErrorDto error = ErrorDto.builder()
                .status(ex.getStatusCode().value()) //Este usa HttpStatusCode no HttpStatus por ello es getStatusCode
                .error(ex.getStatusCode().toString()) //Usa toString porque HttpStatusCode no tiene getReasonPhrase
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    // Manejar Validaciones (@Valid), cuando fallan los @NotBlank, @Email, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull ErrorDto> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message("Error en la validación de campos: " + errors.toString())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Manejar cualquier otro error inesperado (NullPointer, SQL fallido, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull ErrorDto> handleGenericException(Exception ex){
        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Ocurrió un error inesperado en el servidor")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Manejar error con la anotacion Authorize
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException ex){
        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Unathorized")
                .message("Acceso denegado")
                .build();

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
