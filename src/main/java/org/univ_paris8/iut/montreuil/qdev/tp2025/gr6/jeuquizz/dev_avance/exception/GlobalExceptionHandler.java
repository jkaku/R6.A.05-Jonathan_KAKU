package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "Non trouvé", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(BusinessRuleException ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), "Conflit Métier", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedActionException ex) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN.value(), "Accès Refusé", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + " : " + fe.getDefaultMessage())
                .toList();
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "Erreur de Validation", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        ex.printStackTrace(); // Pour les logs console
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur Interne", List.of("Une erreur inattendue est survenue"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}