package com.damzxyno.foodstore.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.damzxyno.foodstore.entity.Admin;
import com.damzxyno.foodstore.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;


public class UserInfoDetails implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(Admin admin) {
        name = admin.getUsername();
        password = admin.getPassword();
        authorities = List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    public UserInfoDetails(Customer customer) {
        name = customer.getUsername();
        password = customer.getPassword();
        authorities = List.of(new SimpleGrantedAuthority("CUSTOMER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}