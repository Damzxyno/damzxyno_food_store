package com.damzxyno.foodstore.dto.request.customer;

import com.damzxyno.foodstore.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating customers
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreationRequest {
    private String username;
    private String password;
    private Address address;
}
