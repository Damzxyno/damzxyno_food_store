package com.damzxyno.foodstore.dto.response.identity;

import com.damzxyno.foodstore.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String message;
    private boolean isSuccess;
    private String username;
    private Long id;
    private UserType userType;
}
