package com.damzxyno.foodstore.controller.old;

import com.damzxyno.foodstore.dto.request.customer.CustomerCreationRequest;
import com.damzxyno.foodstore.dto.request.customer.CustomerModificationRequest;
import com.damzxyno.foodstore.dto.response.customer.CustomerDetailsResponse;
import com.damzxyno.foodstore.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService custService;

    @GetMapping
    public ResponseEntity<List<CustomerDetailsResponse>> getAllCustomers(){
        var res = custService.getAll();
        return ResponseEntity.ok(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDetailsResponse> getCustomerById(@PathVariable(name = "id") Long id){
        var resp = custService.getCustomerById(id);
        return resp.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Long> addNewCustomer(@RequestBody CustomerCreationRequest request){
        var res = custService.createCustomer(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> modifyCustomerById(@PathVariable(name = "id") Long id, @RequestBody CustomerModificationRequest request){
        request.setCustId(id);
        var res = custService.modifyCustomer(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCustomerById(){
        return null;
    }
}
