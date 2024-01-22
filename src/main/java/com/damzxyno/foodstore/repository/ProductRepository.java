package com.damzxyno.foodstore.repository;

import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * This class helps to interact with the product table on the database
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * This method finds products using description and category
     * @param searchItem
     * @param category
     * @param pageRequest
     * @return
     */
    @Query("SELECT P FROM Product P WHERE EXISTS (SELECT 1 FROM P.category c WHERE c IN ?1) AND P.category IN ?2")
    Page<Product> findAllByDescriptionContainingAndCategoryContaining(String searchItem, List<ProductCategory> category, PageRequest pageRequest);

    /**
     * This method finds product using only description
     * @param searchItem
     * @param pageRequest
     * @return
     */
    @Query("SELECT P FROM Product P WHERE LOWER(P.description) LIKE CONCAT('%', LOWER(?1), '%')")
    Page<Product> findAllByDescriptionContaining(String searchItem, PageRequest pageRequest);

    /**
     * This method finds product using only category
     * @param category
     * @param pageRequest
     * @return
     */

    @Query("SELECT P FROM Product P WHERE P.category IN ?1")
    Page<Product> findAllByCategoryContaining(List<ProductCategory> category, PageRequest pageRequest);

    /**
     * This method finds expired products
     * @param currentDate
     * @return
     */
    @Query("SELECT P FROM Product P WHERE P.expiryDate <= :currentDate")
    Page<Product> getExpired(LocalDate currentDate, PageRequest pageRequest);
}
