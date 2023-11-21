package com.damzxyno.foodstore.dto.request.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerModificationRequest {
    @JsonIgnore
    private Long custId;
    private String username;
    private String password;
}
