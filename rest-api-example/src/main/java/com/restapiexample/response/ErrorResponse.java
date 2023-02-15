package com.restapiexample.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String code;

    private String message;

    public ErrorResponse(com.restapiexample.response.ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
