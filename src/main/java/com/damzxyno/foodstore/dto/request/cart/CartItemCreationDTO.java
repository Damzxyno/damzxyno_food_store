package com.damzxyno.foodstore.dto.request.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating cart items
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemCreationDTO {
    private Long productId;
    private Long customerId;
    private int quantity;
}
