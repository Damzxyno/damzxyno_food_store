package com.damzxyno.foodstore.service.impl;

import com.damzxyno.foodstore.dto.request.customer.CustomerCreationRequest;
import com.damzxyno.foodstore.dto.request.customer.CustomerModificationRequest;
import com.damzxyno.foodstore.dto.response.customer.CustomerDetailsResponse;
import com.damzxyno.foodstore.entity.Address;
import com.damzxyno.foodstore.entity.Customer;
import com.damzxyno.foodstore.repository.AddressRepository;
import com.damzxyno.foodstore.repository.CustomerRepository;
import com.damzxyno.foodstore.service.interfaces.CustomerService;
import com.damzxyno.foodstore.utilities.PasswordHashingUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This service does all customer related implementations
 */

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository custRepo;
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;
    private final PasswordHashingUtil passwordHashingUtil;


    /**
     * This method helps to create a new customer
     * @param request a customer creation request
     * @return the ID of the newly created customer
     */
    @Override
    public Long createCustomer(CustomerCreationRequest request) {
        var customer = Customer.builder()
                .password(passwordHashingUtil.hash(request.getPassword()))
                .username(request.getUsername())
                .build();
        var res = custRepo.save(customer);
        if (request.getAddress() != null){
            var address = Address.builder()
                    .customerId(res.getId())
                    .line1(request.getAddress().getLine1())
                    .line2(request.getAddress().getLine2())
                    .line3(request.getAddress().getLine3())
                    .build();
            addressRepository.save(address);
        }

        return res.getId();
    }

    /**
     * Retrieves customer details using customer ID
     * @param id if customers required
     * @return details of the customer
     */
    @Override
    public Optional<CustomerDetailsResponse> getCustomerById(Long id){
        var customer = custRepo.findById(id);
        return customer.map(value -> mapper.map(value, CustomerDetailsResponse.class));
    }

    /**
     * Helps to modify customer details
     * @param request used to modify customer details
     * @return a boolean to signify success
     */
    @Override
    public boolean modifyCustomer(CustomerModificationRequest request) {
        var customerOpt = custRepo.findById(request.getCustId());
        if (customerOpt.isEmpty()){
            return false;
        }
        var customer = customerOpt.get();
        customer.setUsername(request.getUsername());
        customer.setPassword(passwordHashingUtil.hash(request.getPassword()));
        custRepo.save(customer);
        return true;
    }

    /**
     * Help retrieve all customer's on the database
     * @return list of customer details
     */
    @Override
    public List<CustomerDetailsResponse> getAll() {
        var customers = custRepo.findAll();
        return customers.stream()
                .map(customer -> mapper.map(customer, CustomerDetailsResponse.class))
                .toList();
    }

    /**
     * This method deletes customers by ID
     * @param customerId
     */
    @Override
    public void deleteCustomerByID(long customerId) {
        custRepo.deleteById(customerId);
    }
}