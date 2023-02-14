package com.restapiexample.controller;

import com.restapiexample.dto.ProductDto;
import com.restapiexample.response.ProductAssembler;
import com.restapiexample.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/example/v1", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    private final ProductAssembler productAssembler;

    /**
     * RepresentationModelAssembler 사용
     */
    @GetMapping("/products")
    public CollectionModel<EntityModel<ProductDto>> getProductList(){
        List<EntityModel<ProductDto>> response = productService.getProductList().stream()
                .map(productAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(response, linkTo(methodOn(ProductController.class).getProductList()).withSelfRel());
    }

    @GetMapping("/products/{id}")
    public EntityModel<ProductDto> getProductOne(@PathVariable("id") Long id){
        ProductDto response = productService.getProductOne(id);
        return productAssembler.toModel(response);
    }

//    @GetMapping("/products/{id}")
//    public EntityModel<ProductDto> getProductOne(@PathVariable("id") Long id){
//        return EntityModel.of(
//                    productService.getProductOne(id),
//                    linkTo(methodOn(ProductController.class).getProductOne(id)).withSelfRel()
//                );
//    }

}
