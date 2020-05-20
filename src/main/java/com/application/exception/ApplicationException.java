package com.application.exception;

import lombok.Getter;
import lombok.Setter;

public class ApplicationException extends Exception{
    @Getter
    @Setter
    private String messageCode;

    public ApplicationException(String messageCode) {
        this.messageCode=messageCode;
    }
}
