package com.damzxyno.foodstore.service.interfaces;

import com.damzxyno.foodstore.dto.request.customer.CustomerCreationRequest;
import com.damzxyno.foodstore.dto.request.customer.CustomerModificationRequest;
import com.damzxyno.foodstore.dto.response.customer.CustomerDetailsResponse;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Long createCustomer(CustomerCreationRequest request);
    Optional<CustomerDetailsResponse> getCustomerById(Long id);
    boolean modifyCustomer(CustomerModificationRequest request);

    List<CustomerDetailsResponse> getAll();
}
