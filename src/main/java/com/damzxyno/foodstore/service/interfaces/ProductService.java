package com.damzxyno.foodstore.service.interfaces;

import com.damzxyno.foodstore.dto.request.customer.CustomerModificationRequest;
import com.damzxyno.foodstore.dto.request.product.ProductCreationRequest;
import com.damzxyno.foodstore.dto.request.product.ProductModificationRequest;
import com.damzxyno.foodstore.dto.response.product.PaginatedProductsResponse;
import com.damzxyno.foodstore.dto.response.product.ProductDetailsResponse;
import com.damzxyno.foodstore.enums.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Long createProduct(ProductCreationRequest request);

    Optional<ProductDetailsResponse> getProductById(Long id);
    boolean modifyProduct(ProductModificationRequest request);
    boolean deleteProductById (long id);

    PaginatedProductsResponse getAll(String search, List<ProductCategory> category, int pageNo, int pageSize);

}
