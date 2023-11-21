package com.damzxyno.foodstore.service.impl;

import com.damzxyno.foodstore.dto.request.identity.LoginRequest;
import com.damzxyno.foodstore.dto.response.identity.LoginResponse;
import com.damzxyno.foodstore.enums.UserType;
import com.damzxyno.foodstore.repository.AdminRepository;
import com.damzxyno.foodstore.repository.CustomerRepository;
import com.damzxyno.foodstore.service.interfaces.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class help manage users session. This includes creation and  validation of login tokens.
 */
@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {
    private final CustomerRepository custRepo;
    private final AdminRepository adminRepo;

    @Override
    public LoginResponse Login(LoginRequest request) {
        if (request.getUserType().equals(UserType.ADMIN)){
           return LoginAdmin(request);
        } else {
            return LoginCustomer(request);
        }
    }

    /**
     * This endpoint help log in a customer
     * @param request The request param containing customer's login credentials.
     * @return This is a login response to maintain a session or inform user of wrong login credentials.
     */
    private LoginResponse LoginCustomer(LoginRequest request) {
        var user = custRepo.findCustomerByUsername(request.getUsername());
        if (user.isEmpty()){
            return LoginResponse.builder()
                    .isSuccess(false)
                    .message("User not found!")
                    .build();
        }
        if (!user.get().getPassword().equals(request.getPassword())){
            return LoginResponse.builder()
                    .isSuccess(false)
                    .message("Incorrect password!")
                    .build();
        }

        return LoginResponse.builder()
                .username(user.get().getUsername())
                .id(user.get().getId())
                .isSuccess(true)
                .message("Success")
                .userType(UserType.CUSTOMER)
                .build();
    }

    /**
     * This endpoint help log in an admin
     * @param request The request param containing admin's login credentials.
     * @return This is a login response to maintain a session or inform user of wrong login credentials.
     */
    private LoginResponse LoginAdmin(LoginRequest request) {
        var user = adminRepo.findAdminByUsername(request.getUsername());
        if (user.isEmpty()){
            return LoginResponse.builder()
                    .isSuccess(false)
                    .message("User not found!")
                    .build();
        }
        if (!user.get().getPassword().equals(request.getPassword())){
            return LoginResponse.builder()
                    .isSuccess(false)
                    .message("Incorrect password!")
                    .build();
        }

        return LoginResponse.builder()
                .username(user.get().getUsername())
                .id(user.get().getId())
                .isSuccess(true)
                .message("Success")
                .userType(UserType.ADMIN)
                .build();
    }

}
