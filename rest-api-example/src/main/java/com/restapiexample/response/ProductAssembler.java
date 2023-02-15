package com.restapiexample.response;

import com.restapiexample.controller.ProductController;
import com.restapiexample.dto.ProductDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler implements RepresentationModelAssembler<ProductDto, EntityModel<ProductDto>> {

    @Override
    public EntityModel<ProductDto> toModel(ProductDto entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ProductController.class).getProductV3(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProductsV3()).withRel("list")
        );
    }

    @Override
    public CollectionModel<EntityModel<ProductDto>> toCollectionModel(Iterable<? extends ProductDto> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

}
