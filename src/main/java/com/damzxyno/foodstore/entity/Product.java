package com.damzxyno.foodstore.entity;

import com.damzxyno.foodstore.enums.ProductCategory;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String SKU;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    private LocalDate expiryDate;
    private BigDecimal price;
    private String imageUrl;
}
