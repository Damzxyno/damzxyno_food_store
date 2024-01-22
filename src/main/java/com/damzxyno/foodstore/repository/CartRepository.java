package com.damzxyno.foodstore.repository;

import com.damzxyno.foodstore.entity.Cart;
import com.damzxyno.foodstore.entity.CartId;
import com.damzxyno.foodstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This class helps to interact with the cart table on the database
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
    @Query("SELECT C FROM Cart C WHERE C.customerId = ?1")
    Page<Cart> getProductsByCustomerId(Long customerId, PageRequest request);

    Optional<Cart> getCartByCustomerIdAndProductId(long customerId, long productId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.customerId = ?1")
    int deleteAllByCustomerId(long customerId);

    boolean existsByCustomerIdAndProductId(Long customerId, Long productId);

    @Modifying
    @Query("DELETE FROM Cart C WHERE C.customerId = ?1 AND C.productId = ?2")
    int deleteCartByCustomerIdAndProductId(long customerId, long productId);
}
