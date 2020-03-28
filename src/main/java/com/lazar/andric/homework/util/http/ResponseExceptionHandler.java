package com.lazar.andric.homework.util.http;

import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<HttpErrorInfo> handleNotFoundException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(createHttpErrorInfo(NOT_FOUND, ex, request), NOT_FOUND);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, Exception ex, HttpServletRequest request) {
        final String message = ex.getMessage();

        return new HttpErrorInfo(httpStatus, message, request.getRequestURI());
    }
}
