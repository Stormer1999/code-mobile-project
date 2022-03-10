package com.example.code_mobile.repository;

import com.example.code_mobile.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

  // SELECT * FROM Product WHERE name = 'name' LIMIT 1
  Optional<Product> findTopByName(String name);

  // SELECT * FROM Product WHERE name LIKE 'name' AND Stock > stock ORDER BY stock desc
  List<Product> findByNameContainingAndStockGreaterThanOrderByStockDesc(String name, int stock);
}
