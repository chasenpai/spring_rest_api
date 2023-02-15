package com.restapiexample.response;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PRODUCT_NOT_FOUND("001", "존재하지 않는 제품입니다."),
    INTERNAL_SERVER_ERROR("002", "일시적으로 제품 확인이 불가능합니다.");
    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
