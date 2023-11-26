package com.damzxyno.foodstore.utilities;

import com.damzxyno.foodstore.entity.Admin;
import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import com.damzxyno.foodstore.repository.AdminRepository;
import com.damzxyno.foodstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final AdminRepository adminRepo;
    private final ProductRepository productRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run (String [] args) throws Exception{
        seedAdmin();
        seedProducts();
    }
    private void seedAdmin(){
        var admin = Admin.builder()
                .username("admin")
                .password(passwordEncoder.hashPassword("password"))
                .build();
        adminRepo.save(admin);
    }
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
