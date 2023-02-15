package com.restapiexample.response;

import org.hibernate.EntityMode;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.function.Function;

public class LinkResource<T> extends EntityModel<T> {

    public static <T> EntityModel<T> of(WebMvcLinkBuilder linkBuilder, T model, Function<T, ?> resourceFunc){
        return EntityModel.of(model, getSelfLink(linkBuilder, model, resourceFunc));
    }

    private static <T> Link getSelfLink(WebMvcLinkBuilder linkBuilder, T data, Function<T, ?> resourceFunc){
        WebMvcLinkBuilder slash = linkBuilder.slash(resourceFunc.apply(data));
        return slash.withSelfRel();
    }

}
