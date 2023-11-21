package com.damzxyno.foodstore.repository;

import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT P FROM Product P WHERE EXISTS (SELECT 1 FROM P.category c WHERE c IN ?1) AND P.category IN ?2")
    Page<Product> findAllByDescriptionContainingAndCategoryContaining(String searchItem, List<ProductCategory> category, PageRequest pageRequest);
    @Query("SELECT P FROM Product P WHERE P.category IN ?1")
    Page<Product> findAllByDescriptionContaining(List<String> searchItem, PageRequest pageRequest);

    @Query("SELECT P FROM Product P WHERE P.category IN ?1")
    Page<Product> findAllByCategoryContaining(List<ProductCategory> category, PageRequest pageRequest);
}
