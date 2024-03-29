package com.damzxyno.foodstore.dto.response.product;

import com.damzxyno.foodstore.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for product details response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailsResponse {
    private Long id;
    private String SKU;
    private String description;
    private String imageUrl;
    private ProductCategory category;
    private BigDecimal price;
    private LocalDate expiryDate;
}
