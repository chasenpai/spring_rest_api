package com.restapiexample.response;

import com.restapiexample.controller.ProductController;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class ErrorResource extends EntityModel<ErrorResponse> {

    public ErrorResource(ErrorResponse content) {
        super(content);
        add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list"));
    }

}
