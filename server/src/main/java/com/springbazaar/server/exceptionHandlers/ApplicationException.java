package com.springbazaar.server.exceptionHandlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private int errorCode;
    public ApplicationException(int errorCode,String message){
        super(message);
        this.errorCode=errorCode;
    }
}
