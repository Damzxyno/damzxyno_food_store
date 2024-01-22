package com.damzxyno.foodstore.dto.response.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class returns customer details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDetailsResponse {
    private Long id;
    private String username;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
}
