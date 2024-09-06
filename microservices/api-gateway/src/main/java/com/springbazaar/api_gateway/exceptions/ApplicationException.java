package com.springbazaar.api_gateway.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private int errorCode;
    public ApplicationException(int errorCode,String message){
        super(message);
        this.errorCode=errorCode;
    }
}
