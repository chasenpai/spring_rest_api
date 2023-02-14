package com.restapiexample.dto;

import com.restapiexample.entity.Product;
import lombok.Data;

@Data
public class ProductDto {

    private long id;

    private String name;

    private int price;

    private int stock;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }
}
