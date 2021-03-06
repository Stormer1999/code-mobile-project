package com.example.code_mobile.service;

import com.example.code_mobile.controller.request.ProductRequest;
import com.example.code_mobile.model.Product;
import java.util.List;

public interface ProductService {
  List<Product> getAllProducts();

  Product getProductById(Long id);

  Product createProduct(ProductRequest productRequest);

  Product updateProduct(ProductRequest productRequest, Long id);

  void deleteProduct(Long id);

  Product getProductByName(String name);

  List<Product> getProductByNameAndStock(String name, int stock);

  List<Product> getProductOutOfStock();

  List<Product> getProductByNameAndPrice(String name, int price);
}
