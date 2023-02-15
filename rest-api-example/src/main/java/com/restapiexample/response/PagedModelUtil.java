package com.restapiexample.response;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.function.Function;

public class PagedModelUtil {

    public static <T> PagedModel<EntityModel<T>> getEntityModels(PagedResourcesAssembler<T> assembler,
                                                                 Page<T> page, Class<?> cls,
                                                                 Function<T, ?> selfLinkFunc){
        WebMvcLinkBuilder linkBuilder = linkTo(cls);
        return assembler.toModel(page, model -> com.restapiexample.response.LinkResource.of(linkBuilder, model, selfLinkFunc::apply));
    }

    public static <T> PagedModel<EntityModel<T>> getEntityModels(PagedResourcesAssembler<T> assembler,
                                                                 Page<T> page,
                                                                 WebMvcLinkBuilder linkBuilder,
                                                                 Function<T, ?> selfLinkFunc){

        return assembler.toModel(page, model -> com.restapiexample.response.LinkResource.of(linkBuilder, model, selfLinkFunc::apply));
    }

}
