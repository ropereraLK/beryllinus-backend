package com.beryllinus.backend.advice;

import com.beryllinus.backend.exceptions.RoomClassConfigNotFoundException;
import com.beryllinus.backend.exceptions.RoomConfigNotFoundException;
import com.beryllinus.backend.exceptions.RoomNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RoomExceptionHandler {

    /**
     * Room not found.
     *
     * Example:
     * GET /rooms/999
     */
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomNotFound(
            RoomNotFoundException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                Instant.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                ApiErrorCode.ROOM_NOT_FOUND,
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }

    /**
     * Room configuration not found.
     *
     * Example:
     * Room configuration does not exist for a given date.
     */
    @ExceptionHandler(RoomConfigNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomConfigNotFound(
            RoomConfigNotFoundException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                Instant.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                ApiErrorCode.ROOM_CONFIG_NOT_FOUND,
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }

    /**
     * Room class configuration not found.
     *
     * Example:
     * Seasonal pricing configuration missing.
     */
    @ExceptionHandler(RoomClassConfigNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomClassConfigNotFound(
            RoomClassConfigNotFoundException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                Instant.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                ApiErrorCode.ROOM_CLASS_CONFIG_NOT_FOUND,
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }

}