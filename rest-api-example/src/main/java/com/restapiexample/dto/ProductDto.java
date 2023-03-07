package com.restapiexample.dto;

import com.restapiexample.entity.Product;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private long id;

    private String name;

    private int price;

    private int stock;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.createDate = product.getCreatedDate();
        this.lastModifiedDate = product.getLastModifiedDate();
    }

    public static Page<ProductDto> entityToDto(Page<Product> entityPage){
        return entityPage.map(
                ProductDto::new
        );
    }

}
