package com.damzxyno.foodstore.units;

import com.damzxyno.foodstore.dto.request.customer.CustomerCreationRequest;
import com.damzxyno.foodstore.dto.request.customer.CustomerModificationRequest;
import com.damzxyno.foodstore.dto.response.customer.CustomerDetailsResponse;
import com.damzxyno.foodstore.entity.Address;
import com.damzxyno.foodstore.entity.Customer;
import com.damzxyno.foodstore.repository.AddressRepository;
import com.damzxyno.foodstore.repository.CustomerRepository;
import com.damzxyno.foodstore.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository custRepo;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        // Arrange
        CustomerCreationRequest request = CustomerCreationRequest.builder()
                .username("testUser")
                .password("testPassword")
                .address(Address.builder()
                        .line1("123 Main St")
                        .line2("Apt 4")
                        .line3("City")
                        .build())
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        when(custRepo.save(any(Customer.class))).thenReturn(customer);

        // Act
        Long customerId = customerService.createCustomer(request);

        // Assert
        assertEquals(1L, customerId);
        verify(custRepo, times(1)).save(any(Customer.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testGetCustomerById() {
        // Arrange
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(customerId)
                .username("testUser")
                .password("testPassword")
                .build();
        CustomerDetailsResponse expectedResponse = CustomerDetailsResponse.builder()
                .username("testUser")
                .build();
        when(custRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(mapper.map(customer, CustomerDetailsResponse.class)).thenReturn(expectedResponse);

        // Act
        Optional<CustomerDetailsResponse> result = customerService.getCustomerById(customerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedResponse, result.get());
        verify(custRepo, times(1)).findById(customerId);
        verify(mapper, times(1)).map(customer, CustomerDetailsResponse.class);
    }

    @Test
    void testGetCustomerByIdWhenCustomerNotExists() {
        // Arrange
        Long customerId = 1L;
        when(custRepo.findById(customerId)).thenReturn(Optional.empty());

        // Act
        Optional<CustomerDetailsResponse> result = customerService.getCustomerById(customerId);

        // Assert
        assertTrue(result.isEmpty());
        verify(custRepo, times(1)).findById(customerId);
        verify(mapper, never()).map(any(), eq(CustomerDetailsResponse.class));
    }

    @Test
    void testModifyCustomer() {
        // Arrange
        CustomerModificationRequest request = new CustomerModificationRequest();
        request.setCustId(1L);
        request.setUsername("newUsername");
        request.setPassword("newPassword");

        Customer existingCustomer = Customer.builder()
                .id(request.getCustId())
                .username("oldUsername")
                .password("oldPassword")
                .build();
        when(custRepo.findById(request.getCustId())).thenReturn(Optional.of(existingCustomer));

        // Act
        boolean result = customerService.modifyCustomer(request);

        // Assert
        assertTrue(result);
        assertEquals("newUsername", existingCustomer.getUsername());
        assertEquals("newPassword", existingCustomer.getPassword());
        verify(custRepo, times(1)).save(existingCustomer);
    }

    @Test
    void testModifyCustomerWhenCustomerNotExists() {
        // Arrange
        CustomerModificationRequest request = new CustomerModificationRequest();
        request.setCustId(1L);
        request.setUsername("newUsername");
        request.setPassword("newPassword");

        when(custRepo.findById(request.getCustId())).thenReturn(Optional.empty());

        // Act
        boolean result = customerService.modifyCustomer(request);

        // Assert
        assertFalse(result);
        verify(custRepo, never()).save(any(Customer.class));
    }

    @Test
    void testGetAll() {
        // Arrange
        Customer customer1 = Customer.builder().id(1L).username("user1").password("pass1").build();
        Customer customer2 = Customer.builder().id(2L).username("user2").password("pass2").build();
        List<Customer> customers = List.of(customer1, customer2);
        when(custRepo.findAll()).thenReturn(customers);

        CustomerDetailsResponse response1 = CustomerDetailsResponse.builder()
                .username("user1")
                .build();
        CustomerDetailsResponse response2 = CustomerDetailsResponse.builder()
                .username("user2")
                .build();
        when(mapper.map(customer1, CustomerDetailsResponse.class)).thenReturn(response1);
        when(mapper.map(customer2, CustomerDetailsResponse.class)).thenReturn(response2);

        // Act
        List<CustomerDetailsResponse> result = customerService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));
        verify(custRepo, times(1)).findAll();
        verify(mapper, times(1)).map(customer1, CustomerDetailsResponse.class);
        verify(mapper, times(1)).map(customer2, CustomerDetailsResponse.class);
    }
}
