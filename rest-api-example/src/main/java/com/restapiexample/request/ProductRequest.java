package com.restapiexample.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {

    private long id;

    private String name;

    private int price;

    private int stock;

}
