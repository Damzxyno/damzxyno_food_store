package com.damzxyno.foodstore.service.impl;

import com.damzxyno.foodstore.dto.request.customer.CustomerCreationRequest;
import com.damzxyno.foodstore.dto.request.customer.CustomerModificationRequest;
import com.damzxyno.foodstore.dto.response.customer.CustomerDetailsResponse;
import com.damzxyno.foodstore.entity.Address;
import com.damzxyno.foodstore.entity.Customer;
import com.damzxyno.foodstore.repository.AddressRepository;
import com.damzxyno.foodstore.repository.CustomerRepository;
import com.damzxyno.foodstore.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository custRepo;
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;


    @Override
    public Long createCustomer(CustomerCreationRequest request) {
        var customer = Customer.builder()
                .password(request.getPassword())
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

    @Override
    public Optional<CustomerDetailsResponse> getCustomerById(Long id){
        var customer = custRepo.findById(id);
        if (customer.isPresent()){
            return Optional.ofNullable(mapper.map(customer, CustomerDetailsResponse.class));
        }
        return Optional.empty();
    }

    @Override
    public boolean modifyCustomer(CustomerModificationRequest request) {
        var customerOpt = custRepo.findById(request.getCustId());
        if (customerOpt.isEmpty()){
            return false;
        }
        var customer = customerOpt.get();
        customer.setUsername(request.getUsername());
        customer.setPassword(request.getPassword());
        custRepo.save(customer);
        return true;
    }

    @Override
    public List<CustomerDetailsResponse> getAll() {
        var customers = custRepo.findAll();
        return customers.stream()
                .map(customer -> mapper.map(customer, CustomerDetailsResponse.class))
                .toList();
    }
}