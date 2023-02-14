package com.restapiexample.response;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PRODUCT_NOT_FOUND(404, "001", "존재하지 않는 제품입니다."),
    INTERNAL_SERVER_ERROR(500, "002", "일시적으로 재고 확인이 불가능합니다.");
    private int result;
    private String code;
    private String message;

    ErrorCode(int result, String code, String message) {
        this.result = result;
        this.code = code;
        this.message = message;
    }
}
