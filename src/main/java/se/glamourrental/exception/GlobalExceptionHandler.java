package se.glamourrental.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // jag visar 404 om personen eller klänningen inte finns.
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> notFound(NotFoundException ex, HttpServletRequest request) {
        return makeError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // Jag änvnder 409 om någon försöker ta bort något som är kopplat.
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> conflict(ConflictException ex, HttpServletRequest request) {
        return makeError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // Här kontrollerar jag att uppgifterna är rätt, till exempel namn och pris.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalidData(MethodArgumentNotValidException ex,
                                                HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining("; "));
        return makeError(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> invalidJson(HttpMessageNotReadableException ex,
                                                HttpServletRequest request) {
        return makeError(HttpStatus.BAD_REQUEST, "JSON saknas eller har fel format", request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> invalidId(MethodArgumentTypeMismatchException ex,
                                              HttpServletRequest request) {
        return makeError(HttpStatus.BAD_REQUEST, "Felaktigt id i adressen", request);
    }

    private ResponseEntity<ApiError> makeError(HttpStatus status, String message,
                                               HttpServletRequest request) {
        ApiError error = new ApiError(LocalDateTime.now(), status.value(),
                status.getReasonPhrase(), message, request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
