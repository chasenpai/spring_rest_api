package com.restapiexample.response;

import com.restapiexample.controller.ProductController;
import com.restapiexample.dto.ProductDto;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ProductResource extends EntityModel<ProductDto> {

    public ProductResource(ProductDto content) {
        super(content);
        add(linkTo(methodOn(ProductController.class).getProductV2(content.getId())).withSelfRel());
        add(linkTo(methodOn(ProductController.class).getAllProductsV1()).withRel("list"));
    }

}
