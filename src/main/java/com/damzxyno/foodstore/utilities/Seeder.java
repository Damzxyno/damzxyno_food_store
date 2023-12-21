package com.damzxyno.foodstore.utilities;

import com.damzxyno.foodstore.entity.Admin;
import com.damzxyno.foodstore.entity.Customer;
import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import com.damzxyno.foodstore.repository.AdminRepository;
import com.damzxyno.foodstore.repository.CustomerRepository;
import com.damzxyno.foodstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class runs immediately the program starts, it helps to seed the database with initial records to work with.
 */
@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final AdminRepository adminRepo;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * This method calls all neccessary seeding methods
     * @param args
     */
    @Override
    public void run (String [] args){
        seedAdmin();
        seedCustomer();
        seedProducts();
    }

    /**
     * This method seeds a default admin
     */
    private void seedAdmin(){
        var admin = Admin.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .build();
        adminRepo.save(admin);
    }

    /**
     * This method seeds initial customer.
     */
    private void seedCustomer(){
        var customer = Customer.builder()
                .username("customer")
                .password(passwordEncoder.encode("password"))
                .build();
        customerRepository.save(customer);
    }

    /**
     * This method seeds initial 10 products of the system.
     */
    private void seedProducts(){
        var products = new ArrayList<>(
                List.of(Product.builder()
                                .category(ProductCategory.FRUIT)
                                .description("British Apples")
                                .price(BigDecimal.valueOf(1.5))
                                .SKU("BRIT-APP")
                        .build(),
                        Product.builder()
                                .category(ProductCategory.FRUIT)
                                .description("Grapes")
                                .price(BigDecimal.valueOf(2.5))
                                .SKU("GRAPE")
                        .build(),
                        Product.builder()
                                .category(ProductCategory.FRUIT)
                                .description("African Orange")
                                .price(BigDecimal.valueOf(1.56))
                                .SKU("ORANGE")
                        .build(),
                        Product.builder()
                                .category(ProductCategory.FRUIT)
                                .description("Pawpaw")
                                .price(BigDecimal.valueOf(7.5))
                                .SKU("PAWPAW")
                        .build()
                ));
        productRepo.saveAll(products);
    }


}
