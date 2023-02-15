package com.restapiexample.controlleradvice;

import com.restapiexample.controller.ProductController;
import com.restapiexample.exception.ProductNotFoundException;
import com.restapiexample.response.ErrorCode;
import com.restapiexample.response.ErrorResource;
import com.restapiexample.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ProductExceptionHandler {

    /**
     * 존재하지 않는 제품 정보 요청 시
     */
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResource handler(ProductNotFoundException exception){
        return new ErrorResource(new ErrorResponse(exception.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResource handler(Exception exception){
        return new ErrorResource(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
