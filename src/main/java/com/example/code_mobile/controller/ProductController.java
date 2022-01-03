package com.example.code_mobile.controller;

import com.example.code_mobile.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final AtomicLong counter = new AtomicLong();
    private final List<Product> products = new ArrayList<>();


    @GetMapping()
    public List<Product> getProducts(){
        return products;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id){
        return products.stream()
                .filter(result -> result.getId() == id)
                .findFirst().orElseThrow();
    }

    @PostMapping()
    public Product addProduct(@RequestBody Product product){
        Product data = new Product(counter.incrementAndGet(),
                product.getName(),
                product.getImage(),
                product.getPrice(),
                product.getStock()
                );
        products.add(data);
        return data;
    }

    @PutMapping("/{id}")
    public void editProduct(
            @RequestBody Product product,
            @PathVariable long id
    ){
        products.stream()
                .filter(result -> result.getId() == id)
                .findFirst()
                .ifPresentOrElse(result -> {
                    result.setName(product.getName());
                    result.setImage(product.getImage());
                    result.setPrice(product.getPrice());
                    result.setStock(product.getStock());
                }, () -> {
                    //todo
                });
    }
}