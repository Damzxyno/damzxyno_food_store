package com.damzxyno.foodstore.service.impl;


import com.damzxyno.foodstore.dto.UserInfoDetails;
import com.damzxyno.foodstore.repository.AdminRepository;
import com.damzxyno.foodstore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var admin = adminRepository.findAdminByUsername(username);
        var customer = customerRepository.findCustomerByUsername(username);
        UserInfoDetails userInfoDetail = null;
        if (admin.isPresent()){
            userInfoDetail = new UserInfoDetails(admin.get());
        } else if ( customer.isPresent()){
            userInfoDetail = new UserInfoDetails(customer.get());
        }
        if (userInfoDetail == null){
            throw new UsernameNotFoundException("User not found " + username);
        }
        return userInfoDetail;
    }
}