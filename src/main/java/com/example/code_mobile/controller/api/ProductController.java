package com.example.code_mobile.controller.api;

import com.example.code_mobile.controller.request.ProductRequest;
import com.example.code_mobile.exception.ProductNotFoundException;
import com.example.code_mobile.exception.ValidationException;
import com.example.code_mobile.model.Product;
import com.example.code_mobile.repository.ProductRepository;
import com.example.code_mobile.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final StorageService storageService;

  private final ProductRepository productRepository;

  @Autowired
  public ProductController(StorageService storageService, ProductRepository productRepository) {
    this.storageService = storageService;
    this.productRepository = productRepository;
  }

  @GetMapping()
  public List<Product> getProducts() {
    return productRepository.findAll();
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable long id) {
    return productRepository.findById(id).stream()
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
    Product product = new Product();
    product
        .setName(productRequest.getName())
        .setImage(product.getImage())
        .setPrice(productRequest.getPrice())
        .setStock(productRequest.getStock());
    return productRepository.save(product);
  }

  @PutMapping("/{id}")
  public void editProduct(@RequestBody Product product, @PathVariable long id) {
    productRepository.findById(id).stream()
        .filter(result -> result.getId() == id)
        .findFirst()
        .ifPresentOrElse(
            result -> {
              result.setName(product.getName());
              result.setImage(product.getImage());
              result.setPrice(product.getPrice());
              result.setStock(product.getStock());
              productRepository.save(result);
            },
            () -> {
              throw new ProductNotFoundException(id);
            });
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
    productRepository.findById(id).stream()
        .filter(result -> result.getId() == id)
        .findFirst()
        .ifPresentOrElse(
                productRepository::delete,
            () -> {
              throw new ProductNotFoundException(id);
            });
  }
}
