package com.restapiexample.service;

import com.restapiexample.dto.ProductDto;
import com.restapiexample.entity.Product;
import com.restapiexample.exception.ProductNotFoundException;
import com.restapiexample.repository.ProductRepository;
import com.restapiexample.request.ProductRequest;
import com.restapiexample.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto getProduct(Long id){

        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            return new ProductDto(product.get());
        }else{
            throw new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND); //존재하지 않는 제품 정보 요청 시 예외처리
        }

    }

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto saveProduct(ProductRequest request){
        Product product = productRepository.save(new Product(request));
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto updateProduct(ProductRequest request){

        Optional<Product> product = productRepository.findById(request.getId());

        if(product.isPresent()){
            product.get().updateProduct(request);
            return new ProductDto(product.get());
        }else{
            throw new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    @Transactional
    public ProductDto deleteProduct(Long id){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            productRepository.deleteById(product.get().getId());
            return new ProductDto(product.get());
        }else{
            throw new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public Page<ProductDto> getAllProductsPage(Pageable pageable){
        return ProductDto.entityToDto(productRepository.findAll(pageable));
    }

}
