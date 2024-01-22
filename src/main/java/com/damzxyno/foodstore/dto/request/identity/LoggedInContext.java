package com.damzxyno.foodstore.dto.request.identity;

import com.damzxyno.foodstore.enums.UserType;
import lombok.Data;


/**
 * DTO containing logged in customer info
 */
@Data
public class LoggedInContext {
    private Long id;
    private UserType userType;
    private boolean isLoggedIn = false;
}
