package com.damzxyno.foodstore.repository;

import com.damzxyno.foodstore.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class helps to interact with the address table on the database
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
