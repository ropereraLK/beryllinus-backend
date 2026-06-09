package com.beryllinus.backend.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Bean Validation on @RequestBody DTOs
     * <p>
     * Example:
     * POST /rooms
     * {
     * "name": ""
     * }
     *
     * @NotBlank validation fails.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation failed");

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation Failed");
        problem.setDetail(message);

        return problem;
    }

    /**
     * Bean Validation on path variables and request parameters
     * <p>
     * Example:
     * GET /rooms/-1
     *
     * @Positive validation fails.
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleHandlerValidation(
            HandlerMethodValidationException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation Failed");
        problem.setDetail("Request validation failed");

        return problem;
    }

    /**
     * Constraint validation failures.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(
            ConstraintViolationException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Constraint Violation");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    /**
     * Missing request parameter.
     * <p>
     * Example:
     * GET /rooms?date=
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingParameter(
            MissingServletRequestParameterException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Missing Request Parameter");
        problem.setDetail(
                "Required parameter '%s' is missing"
                        .formatted(ex.getParameterName())
        );

        return problem;
    }

    /**
     * Unsupported HTTP method.
     * <p>
     * Example:
     * POST /health
     * when only GET is allowed.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);

        problem.setTitle("Method Not Allowed");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    /**
     * Endpoint not found.
     * <p>
     * Example:
     * GET /api/does-not-exist
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFound(
            NoResourceFoundException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("Resource Not Found");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    /**
     * Catch-all handler.
     * <p>
     * Any unexpected exception will end up here.
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Internal Server Error");
        problem.setDetail(
                "An unexpected error occurred while processing request: "
                        + request.getRequestURI()
        );

        return problem;
    }


}
