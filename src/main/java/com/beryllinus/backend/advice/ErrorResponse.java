package com.beryllinus.backend.advice;

import java.time.Instant;

public record ErrorResponse(Instant timestamp,
                            int status,
                            String error,
                            ApiErrorCode errorCode,
                            String message,
                            String path) {
}
