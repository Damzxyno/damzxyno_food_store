package com.damzxyno.foodstore.repository;

import com.damzxyno.foodstore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This class helps to interact with the customer table on the database
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByUsername(String username);
}
