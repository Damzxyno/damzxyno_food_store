package com.damzxyno.foodstore.dto.request.identity;

import com.damzxyno.foodstore.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO containing login reuest
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String username;
    private String password;
    private UserType userType;
}
