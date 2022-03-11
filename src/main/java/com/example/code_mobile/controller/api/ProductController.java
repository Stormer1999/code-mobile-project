package com.example.code_mobile.controller.api;

import com.example.code_mobile.controller.request.ProductRequest;
import com.example.code_mobile.exception.ValidationException;
import com.example.code_mobile.model.Product;
import com.example.code_mobile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping()
  public List<Product> getProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable Long id) {
    return productService.getProductById(id);
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
    return productService.createProduct(productRequest);
  }

  @PutMapping("/{id}")
  public Product editProduct(
      @Valid ProductRequest productRequest,
      BindingResult bindingResult,
      @PathVariable long id) { // binding must be after @Valid
    if (bindingResult.hasErrors()) {
      bindingResult
          .getFieldErrors()
          .forEach(
              fieldError -> {
                throw new ValidationException(
                    fieldError.getField() + ": " + fieldError.getDefaultMessage());
              });
    }
    return productService.updateProduct(productRequest, id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
  }

  @GetMapping(path = "/search", params = "name")
  public Product searchProductByName(@RequestParam String name) {
    return productService.getProductByName(name);
  }

  @GetMapping(
      path = "/search",
      params = {"name", "stock"})
  public List<Product> searchProductByNameAndStock(@RequestParam String name, int stock) {
    return productService.getProductByNameAndStock(name, stock);
  }

  @GetMapping("/out-of-stock")
  public List<Product> checkOutOfStock() {
    return productService.getProductOutOfStock();
  }

  @GetMapping(
      path = "/search",
      params = {"name", "price"})
  public List<Product> searchProductByNameAndPrice(@RequestParam String name, int price) {
    return productService.getProductByNameAndPrice(name, price);
  }
}
