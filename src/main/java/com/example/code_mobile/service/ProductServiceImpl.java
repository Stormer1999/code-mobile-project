package com.example.code_mobile.service;

import com.example.code_mobile.controller.request.ProductRequest;
import com.example.code_mobile.exception.ProductNotFoundException;
import com.example.code_mobile.model.Product;
import com.example.code_mobile.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

  private final StorageService storageService;

  private final ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(StorageService storageService, ProductRepository productRepository) {
    this.storageService = storageService;
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
  }

  @Override
  public Product getProductById(Long id) {
    Optional<Product> product = productRepository.findById(id); // Optional is chance to be null
    if (product.isPresent()) {
      return product.get();
    }
    throw new ProductNotFoundException(id);
  }

  @Override
  public Product createProduct(ProductRequest productRequest) {
    String fileName = storageService.store(productRequest.getImage());
    Product product = new Product();
    product
        .setName(productRequest.getName())
        .setImage(fileName)
        .setPrice(productRequest.getPrice())
        .setStock(productRequest.getStock());
    return productRepository.save(product);
  }

  @Override
  public Product updateProduct(ProductRequest productRequest, Long id) {
    String fileName = storageService.store(productRequest.getImage());
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      Product existingProduct = product.get();
      if (fileName != null) {
        existingProduct.setImage(fileName);
      }
      existingProduct
          .setName(productRequest.getName())
          .setPrice(productRequest.getPrice())
          .setStock(productRequest.getStock());
      return productRepository.save(existingProduct);
    }
    throw new ProductNotFoundException(id);
  }

  @Override
  public void deleteProduct(Long id) {
    try {
      productRepository.deleteById(id);
    } catch (Exception e) {
      throw new ProductNotFoundException(id);
    }
  }

  @Override
  public Product getProductByName(String name) {
    Optional<Product> product = productRepository.findTopByName(name);
    if (product.isPresent()) {
      return product.get();
    }
    throw new ProductNotFoundException(name);
  }

  @Override
  public List<Product> getProductByNameAndStock(String name, int stock) {
    return productRepository.findByNameContainingAndStockGreaterThanOrderByStockDesc(name, stock);
  }

  @Override
  public List<Product> getProductOutOfStock() {
    return productRepository.checkOutOfStock();
  }

  @Override
  public List<Product> getProductByNameAndPrice(String name, int price) {
    return productRepository.searchNameAndPrice(name, price);
  }
}
