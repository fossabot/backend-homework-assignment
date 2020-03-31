package com.lazar.andric.homework.util.http;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorResponse {
    private List<HttpErrorInfo> violations = new ArrayList<>();
}
