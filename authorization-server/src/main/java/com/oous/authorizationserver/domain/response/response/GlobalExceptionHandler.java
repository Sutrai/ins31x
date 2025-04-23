package com.oous.authorizationserver.domain.response.response;

import com.oous.authorizationserver.domain.response.error.Data;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.oous.authorizationserver.domain.response.error.ErrorResponse;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final boolean enableStacktrace;

    public GlobalExceptionHandler(
            @Value("&{server.error.include-stacktrace}") String enableStacktrace) {
        this.enableStacktrace = ("always".equals(enableStacktrace));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .data(Data.builder()
                        .error("Internal Server Error")
                        .techMessage(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()))
                        .method(request.getMethod())
                        .request(request.getRequestURI())
                        .build())
                .stacktrace(enableStacktrace ? ex.getStackTrace() : null)
                .success(false).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private void logRequestException(HttpServletRequest request, Exception exception) {
        log.debug("Unexpected exception processing request: " + request.getRequestURI());
        log.error("Exception: ", exception);
    }
}
