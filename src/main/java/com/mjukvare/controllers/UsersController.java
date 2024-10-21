package com.mjukvare.controllers;

import com.mjukvare.controllers.requests.CreateUserNoValidationRequest;
import com.mjukvare.controllers.requests.CreateUserRequest;
import com.mjukvare.models.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

/**
 * Learning error handling in Spring.
 */
@RestController
@RequestMapping("api/users")
public class UsersController {

    private final Logger log = LoggerFactory.getLogger(UsersController.class);

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        log.info("Getting users called");
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserNoValidationRequest request) throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/users/%s".formatted(UUID.randomUUID()))).build();
    }

    @PostMapping("with-validation")
    public ResponseEntity<Void> createUserWithValidation(@Valid @RequestBody CreateUserRequest request) throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/users/%s".formatted(UUID.randomUUID()))).build();
    }

    @GetMapping("poor-handling")
    public ResponseEntity<Void> poorHandling() {
        throw new IllegalArgumentException("Something went wrong");
    }
}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAllExceptions(Exception ex) {
        return ErrorResponse
                .builder(ex, ProblemDetail.forStatusAndDetail(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage())
                )
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var violations = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation error");
        List<String> v = ex.getFieldErrors().stream()
                .map(violation -> "%s: %s". formatted(violation.getField(), violation.getDefaultMessage()))
                .toList();

        violations.setProperty("violations", v);
        return violations;
    }
}