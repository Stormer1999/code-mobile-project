package com.example.code_mobile.controller;

import com.example.code_mobile.exception.ProductNotFoundException;
import com.example.code_mobile.model.Product;
import com.example.code_mobile.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final AtomicLong counter = new AtomicLong();
  private final List<Product> products = new ArrayList<>();
  private final StorageService storageService;

  @Autowired
  public ProductController(StorageService storageService) {
    this.storageService = storageService;
  }

  @GetMapping()
  public List<Product> getProducts() {
    return products;
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable long id) {
    return products.stream()
        .filter(result -> result.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Product addProduct(@RequestParam MultipartFile file
  )
//          , @RequestBody Product product)
  {
    String fileName = storageService.store(file);
//    Product data =
//        new Product(
//            counter.incrementAndGet(),
//            product.getName(),
//            product.getImage(),
//            product.getPrice(),
//            product.getStock());
//    products.add(data);
//    return data;
    return null;
  }

  @PutMapping("/{id}")
  public void editProduct(@RequestBody Product product, @PathVariable long id) {
    products.stream()
        .filter(result -> result.getId() == id)
        .findFirst()
        .ifPresentOrElse(
            result -> {
              result.setName(product.getName());
              result.setImage(product.getImage());
              result.setPrice(product.getPrice());
              result.setStock(product.getStock());
            },
            () -> {
              throw new ProductNotFoundException(id);
            });
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
    products.stream()
        .filter(result -> result.getId() == id)
        .findFirst()
        .ifPresentOrElse(
            products::remove,
            () -> {
              throw new ProductNotFoundException(id);
            });
  }
}
