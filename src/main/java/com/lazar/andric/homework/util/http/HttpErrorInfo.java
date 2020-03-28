package com.lazar.andric.homework.util.http;

import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
@Data
public class HttpErrorInfo {
    private final ZonedDateTime timestamp;
    private final String message;
    private final HttpStatus httpStatus;
    private final String path;

    public HttpErrorInfo(HttpStatus httpStatus, String message, String path) {
        timestamp = ZonedDateTime.now();
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
    }
}
