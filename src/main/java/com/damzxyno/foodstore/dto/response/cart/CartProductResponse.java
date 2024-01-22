package com.damzxyno.foodstore.dto.response.cart;

import com.damzxyno.foodstore.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response DTO for cart
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductResponse {
    private Long id;
    private String SKU;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
    private int quantity;
    private String imageUrl;
}
