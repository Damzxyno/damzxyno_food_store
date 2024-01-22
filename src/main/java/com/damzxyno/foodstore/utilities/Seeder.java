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
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class runs immediately the program starts, it helps to seed the database with initial records to work with.
 */
@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final AdminRepository adminRepo;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepo;
    private final PasswordHashingUtil passwordHashingUtil;

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
        var admins = new ArrayList<Admin>();
        admins.add(Admin.builder()
                .username("admin1")
                .password(passwordHashingUtil.hash("admin1"))
                .build());
        admins.add(Admin.builder()
                .username("admin2")
                .password(passwordHashingUtil.hash("admin2"))
                .build());
        adminRepo.saveAll(admins);
    }

    /**
     * This method seeds initial customer.
     */
    private void seedCustomer(){
        var customers = new ArrayList<Customer>();
        customers.add(Customer.builder()
                .username("customer1")
                .password(passwordHashingUtil.hash("customer1"))
                .build());
        customers.add(Customer.builder()
                .username("customer2")
                .password(passwordHashingUtil.hash("customer2"))
                .build());
        customerRepository.saveAll(customers);
    }

    /**
     * This method seeds initial 10 products of the system.
     */
    private void seedProducts(){
        var products = new ArrayList<Product>();
        products.add(Product.builder()
                                .category(ProductCategory.FRUIT)
                                .description("Apples")
                                .price(BigDecimal.valueOf(1.50))
                                .SKU("APP")
                                .expiryDate(LocalDate.of(2024, 01, 28))
                                .imageUrl("apple.png")
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.FRUIT)
                                .description("Grapes")
                                .price(BigDecimal.valueOf(2.50))
                                .SKU("GRP")
                                .imageUrl("grapes.png")
                                .expiryDate(LocalDate.of(2024, 01, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.FRUIT)
                                .description("Banana")
                                .price(BigDecimal.valueOf(3.10))
                                .SKU("BAN")
                                .imageUrl("banana.png")
                                .expiryDate(LocalDate.of(2024, 01, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.VEGETABLE)
                                .description("Broccoli")
                                .price(BigDecimal.valueOf(3.5))
                                .SKU("BROC")
                                .imageUrl("broccoli.png")
                                .expiryDate(LocalDate.of(2023, 12, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.VEGETABLE)
                                .description("Carrot")
                                .price(BigDecimal.valueOf(1.5))
                                .SKU("CAR")
                                .imageUrl("carrot.png")
                                .expiryDate(LocalDate.of(2023, 12, 31))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.VEGETABLE)
                                .description("Cabbage")
                                .price(BigDecimal.valueOf(6.5))
                                .SKU("Cabbage")
                                .imageUrl("cabbage.png")
                                .expiryDate(LocalDate.of(2024, 02, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.FISH)
                                .description("Salmon")
                                .price(BigDecimal.valueOf(10.5))
                                .SKU("SAL")
                                .imageUrl("salmon.png")
                                .expiryDate(LocalDate.of(2024, 03, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.FISH)
                                .description("Mackerel")
                                .price(BigDecimal.valueOf(13.58))
                                .SKU("MAC")
                                .imageUrl("mackerel.png")
                                .expiryDate(LocalDate.of(2024, 06, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.MEAT)
                                .description("Gammon")
                                .price(BigDecimal.valueOf(10.99))
                                .SKU("GAM")
                                .imageUrl("gammon.png")
                                .expiryDate(LocalDate.of(2024, 07, 11))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.MEAT)
                                .description("Turkey")
                                .price(BigDecimal.valueOf(10.23))
                                .SKU("TUK")
                                .imageUrl("turkey.png")
                                .expiryDate(LocalDate.of(2024, 12, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.MEAT)
                                .description("Chicken Breast")
                                .price(BigDecimal.valueOf(12.5))
                                .SKU("CHB")
                                .imageUrl("chicken-breast.png")
                                .expiryDate(LocalDate.of(2025, 01, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.MEAT)
                                .description("Chicken Wings")
                                .price(BigDecimal.valueOf(12.98))
                                .SKU("CHW")
                                .imageUrl("chicken-wing.png")
                                .expiryDate(LocalDate.of(2024, 01, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.DAIRY)
                                .description("Skimmed Cow Milk")
                                .price(BigDecimal.valueOf(1.5))
                                .SKU("MILC")
                                .imageUrl("milk.png")
                                .expiryDate(LocalDate.of(2024, 01, 28))
                        .build());
        products.add(Product.builder()
                                .category(ProductCategory.PASTA)
                                .description("Spaghetti")
                                .price(BigDecimal.valueOf(0.58))
                                .SKU("PAS")
                                .imageUrl("spaghetti.png")
                                .expiryDate(LocalDate.of(2028, 01, 28))
                        .build());
        productRepo.saveAll(products);
    }


}
