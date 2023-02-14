package com.restapiexample.response;

import com.restapiexample.exception.ProductNotFoundException;

public class ErrorResponse {
    private int result;

    private String code;

    private String message;

    public ErrorResponse(ProductNotFoundException exception) {
        this.result = exception.getErrorCode().getResult();
        this.code = exception.getErrorCode().getCode();
        this.message = exception.getErrorCode().getMessage();;
    }

    public ErrorResponse(ErrorCode errorCode){
        this.result = errorCode.getResult();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();;
    }

}
