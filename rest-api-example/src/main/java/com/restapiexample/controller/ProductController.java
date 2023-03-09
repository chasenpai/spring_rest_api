package com.restapiexample.controller;

import com.restapiexample.dto.ProductDto;
import com.restapiexample.request.ProductRequest;
import com.restapiexample.response.PagedModelUtil;
import com.restapiexample.response.ProductAssembler;
import com.restapiexample.response.ProductResource;
import com.restapiexample.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/example", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;
    private final PagedResourcesAssembler<ProductDto> pageAssembler;

    /**
     * ModelEntity
     * - 단일 엔티티에 리소스 추가
     */
    @GetMapping("/v1/products/{id}")
    public EntityModel<ProductDto> getProduct(@PathVariable("id") Long id){
        Link link = Link.of("http://localhost8080/api/example/v1/products/" + id, "self"); //수동 링크 생성
        return EntityModel.of(
                productService.getProduct(id),
                //WebMvcLinkBuilder 를 사용한 링크 생성 단순화
                linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list")
        );
    }

    /**
     * CollectionModel
     * - 엔티티 컬렉션에 리소스 추가
     */
    @GetMapping("/v1/products")
    public CollectionModel<EntityModel<ProductDto>> getAllProducts(){
        List<EntityModel<ProductDto>> response = productService.getAllProducts()
                .stream()
                .map(
                        list -> EntityModel.of(
                                list,
                                linkTo(methodOn(ProductController.class).getProduct(list.getId())).withSelfRel(),
                                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list")
                        )
                )
                .collect(Collectors.toList());
        return CollectionModel.of(
                response,
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()
        );
    }

    @PostMapping("/v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ProductDto> saveProduct(@RequestBody ProductRequest request){
        ProductDto productDto = productService.saveProduct(request);
        return EntityModel.of(
                productDto,
                linkTo(methodOn(ProductController.class).getProduct(productDto.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list"),
                linkTo(methodOn(ProductController.class).deleteProduct(productDto.getId())).withRel("delete")
        );
    }

    @PutMapping("/v1/products")
    public EntityModel<ProductDto> updateProduct(@RequestBody ProductRequest request){
        ProductDto productDto = productService.updateProduct(request);
        return EntityModel.of(
                productDto,
                linkTo(methodOn(ProductController.class).getProduct(productDto.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list"),
                linkTo(methodOn(ProductController.class).deleteProduct(productDto.getId())).withRel("delete")
        );
    }

    @DeleteMapping("/v1/products/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public EntityModel<ProductDto> deleteProduct(@PathVariable("id") Long id){
        ProductDto productDto = productService.deleteProduct(id);
        return EntityModel.of(
                productDto,
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("list")
        );
    }

    /**
     * EntityModel 상속 클래스 사용
     */
    @GetMapping("/v2/products/{id}")
    public ProductResource getProductV2(@PathVariable("id") Long id){
        return new ProductResource(productService.getProduct(id));
    }

    /**
     * PagedModel
     * - PagedResourceAssembler 를 사용한 페이지 리소스 변환 > 페이징 리소스만 제공된다
     * - PagedModelUtil 커스텀을 사용하여 단일 엔티티에 대한 리소스도 추가
     */
    @GetMapping("/v2/products")
    public PagedModel<EntityModel<ProductDto>> getAllProductsV2(Pageable pageable){
        Page<ProductDto> page = productService.getAllProductsPage(pageable);
        return PagedModelUtil.getEntityModels(
                pageAssembler,
                page,
                linkTo(methodOn(ProductController.class).getAllProductsV2(null)),
                ProductDto::getId
        );
        //return pageAssembler.toModel(page);
    }

    /**
     * RepresentationModelAssembler
     * - 반복되는 EntityModel 과 링크 생성을 피하기 위해 해당 인터페이스를 구현
     */
    @GetMapping("/v3/products/{id}")
    public EntityModel<ProductDto> getProductV3(@PathVariable("id") Long id){
        return productAssembler.toModel(productService.getProduct(id));
    }

    @GetMapping("/v3/products")
    public CollectionModel<EntityModel<ProductDto>> getAllProductsV3(){
        List<EntityModel<ProductDto>> response = productService.getAllProducts()
                .stream()
                .map(productAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(response, linkTo(methodOn(ProductController.class).getAllProductsV3()).withSelfRel());
    }

}
