package com.damzxyno.foodstore.repository;

import com.damzxyno.foodstore.entity.Admin;
import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findAdminByUsername (String username);
}
