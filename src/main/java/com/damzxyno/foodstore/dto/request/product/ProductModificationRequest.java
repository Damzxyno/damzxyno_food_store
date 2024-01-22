package com.damzxyno.foodstore.dto.request.product;

import com.damzxyno.foodstore.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * DTO containing details for product modification
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModificationRequest {
    @JsonIgnore
    private Long id;
    private String SKU;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
}
