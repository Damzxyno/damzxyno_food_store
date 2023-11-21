package com.damzxyno.foodstore.dto.request.product;

import com.damzxyno.foodstore.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
}
