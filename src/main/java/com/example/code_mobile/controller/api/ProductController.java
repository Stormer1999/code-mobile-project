package com.example.code_mobile.controller.api;

import com.example.code_mobile.controller.request.ProductRequest;
import com.example.code_mobile.exception.ProductNotFoundException;
import com.example.code_mobile.exception.ValidationException;
import com.example.code_mobile.model.Product;
import com.example.code_mobile.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final AtomicLong counter = new AtomicLong();
  private final List<Product> products = new ArrayList<>();
  private final StorageService storageService;
  private static final Logger log = LoggerFactory.getLogger(ProductController.class);

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
  public Product addProduct(@Valid ProductRequest productRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult
          .getFieldErrors()
          .forEach(
              fieldError -> {
                throw new ValidationException(
                    fieldError.getField() + ": " + fieldError.getDefaultMessage());
              });
    }

    String fileName = storageService.store(productRequest.getImage());
    Product data =
        new Product(
            counter.incrementAndGet(),
            productRequest.getName(),
            fileName,
            productRequest.getPrice(),
            productRequest.getStock());
    products.add(data);
    return data;
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
