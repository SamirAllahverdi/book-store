package com.example.exception.handler;

import com.example.dto.ExceptionResponse;
import com.example.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidConfirmationKeyException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidConfirmationKeyException(InvalidConfirmationKeyException exception,
                                                                                         WebRequest request) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleBookNotFoundException(BookNotFoundException exception,
                                                                               WebRequest request) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception,
                                                                               WebRequest request) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public final ResponseEntity<ExceptionResponse> handleAlreadyExistException(AlreadyExistException exception,
                                                                               WebRequest request) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(WrongPublisherException.class)
    public final ResponseEntity<ExceptionResponse> handleWrongPublisherException(WrongPublisherException exception,
                                                                                 WebRequest request) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(getMessage(exception))
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    private String getMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(String.format("%s : %s", exception.getParameterName(), exception.getParameterType()))
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(Instant.now())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("{}", response);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


}
