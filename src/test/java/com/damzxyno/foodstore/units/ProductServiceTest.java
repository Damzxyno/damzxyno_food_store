package com.damzxyno.foodstore.units;

import com.damzxyno.foodstore.service.impl.ProductServiceImpl;
import com.damzxyno.foodstore.service.interfaces.ProductService;
import org.junit.jupiter.api.Test;

import com.damzxyno.foodstore.dto.request.product.ProductCreationRequest;
import com.damzxyno.foodstore.dto.request.product.ProductModificationRequest;
import com.damzxyno.foodstore.dto.response.product.PaginatedProductsResponse;
import com.damzxyno.foodstore.dto.response.product.ProductDetailsResponse;
import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import com.damzxyno.foodstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository prodRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        // Arrange
        ProductCreationRequest request = ProductCreationRequest.builder()
                .price(BigDecimal.valueOf(1.0))
                .category(ProductCategory.FRUIT)
                .SKU("ABC123")
                .description("Pineapple")
                .build();
        Product product = new Product();
        product.setId(1L);
        when(prodRepo.save(any(Product.class))).thenReturn(product);
        // Act
        Long productId = productService.createProduct(request);
        // Assert
        assertEquals(1L, productId);
        verify(prodRepo, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById() {
        // Arrange
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .build();
        ProductDetailsResponse expectedResponse = ProductDetailsResponse.builder()
                .id(productId)
                .build();
        when(prodRepo.findById(productId)).thenReturn(Optional.of(product));
        when(mapper.map(product, ProductDetailsResponse.class)).thenReturn(expectedResponse);
        // Act
        Optional<ProductDetailsResponse> result = productService.getProductById(productId);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedResponse, result.get());
        verify(prodRepo, times(1)).findById(productId);
        verify(mapper, times(1)).map(product, ProductDetailsResponse.class);
    }

}