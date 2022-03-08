package com.example.code_mobile.repository;

import com.example.code_mobile.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
