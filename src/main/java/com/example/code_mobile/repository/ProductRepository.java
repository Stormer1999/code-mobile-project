package com.example.code_mobile.repository;

import com.example.code_mobile.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

  // SELECT * FROM Product WHERE name = 'name' LIMIT 1
  Optional<Product> findTopByName(String name);

  // SELECT * FROM Product WHERE name LIKE 'name' AND Stock > stock ORDER BY stock desc
  List<Product> findByNameContainingAndStockGreaterThanOrderByStockDesc(String name, int stock);

  @Query(value = "SELECT * FROM Product WHERE stock = 0", nativeQuery = true)
  List<Product> checkOutOfStock();

  @Query(
      value = "SELECT * FROM Product WHERE name LIKE %:product_name% AND price > :price",
      nativeQuery = true)
  List<Product> searchNameAndPrice(@Param("product_name") String name, @Param("price") int price);
}
