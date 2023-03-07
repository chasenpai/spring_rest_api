package com.restapiexample.entity;

import com.restapiexample.request.ProductRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Product extends com.restapiexample.entity.BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    public Product(ProductRequest request) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
    }

    public void updateProduct(ProductRequest request){
        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
    }

}
