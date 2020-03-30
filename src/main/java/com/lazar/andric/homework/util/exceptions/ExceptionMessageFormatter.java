package com.lazar.andric.homework.util.exceptions;

public class ExceptionMessageFormatter {

    public static String formatEntityNotFoundMessage(String entityName, Long id) {
        return String.format("%s with provided id %s not found",entityName, id);
    }
}
