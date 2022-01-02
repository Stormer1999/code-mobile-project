package com.example.code_mobile.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping()
    public String getProducts(){
        return "all product";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable long id){
        return "product id: " + id ;
    }
}