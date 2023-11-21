package com.damzxyno.foodstore.dto.request.product;

import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationRequest {
    private String SKU;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
}
