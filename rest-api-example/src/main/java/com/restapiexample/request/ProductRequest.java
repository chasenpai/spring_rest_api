package com.restapiexample.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {

    private long id;

    private String name;

    private int price;

    private int stock;

    @Builder
    public ProductRequest(long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

}
