package com.restapiexample.controlleradvice;

import com.restapiexample.exception.ProductNotFoundException;
import com.restapiexample.response.ErrorCode;
import com.restapiexample.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ProductExceptionHandler {

    /**
     * 존재하지 않는 제품의 재고 요청 시
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(ProductNotFoundException exception){
        ErrorResponse response = new ErrorResponse(exception);
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handler(Exception exception){
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.ok().body(response);
    }

}
