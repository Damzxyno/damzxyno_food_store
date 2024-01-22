package com.damzxyno.foodstore.service.impl;

import com.damzxyno.foodstore.dto.request.product.ProductCreationRequest;
import com.damzxyno.foodstore.dto.request.product.ProductModificationRequest;
import com.damzxyno.foodstore.dto.response.product.PaginatedProductsResponse;
import com.damzxyno.foodstore.dto.response.product.ProductDetailsResponse;
import com.damzxyno.foodstore.entity.Product;
import com.damzxyno.foodstore.enums.ProductCategory;
import com.damzxyno.foodstore.repository.ProductRepository;
import com.damzxyno.foodstore.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This helps with all product related implementations
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository prodRepo;
    private final ModelMapper mapper;

    /**
     * Helps to create a product
     * @param request product creation request
     * @return a Long, ID of the newly created product
     */

    @Override
    public Long createProduct (ProductCreationRequest request) {
        var product = Product.builder()
                .price(request.getPrice())
                .category(request.getCategory())
                .SKU(request.getSKU())
                .description(request.getDescription())
                .expiryDate(request.getExpiryDate())
                .build();
        var res = prodRepo.save(product);
        return res.getId();
    }

    /**
     * Retrieves a product detail by ID
     * @param id if the product required
     * @return a product
     */

    @Override
    public Optional<ProductDetailsResponse> getProductById(Long id){
        var product = prodRepo.findById(id);
        if (product.isPresent()){
            var res = mapper.map(product.get(), ProductDetailsResponse.class);
            return Optional.ofNullable(res);
        }
        return Optional.empty();
    }

    /**
     * Helps to modify a product
     * @param request product modification request
     * @return boolean to signify successful modification
     */
    @Override
    public boolean modifyProduct(ProductModificationRequest request) {
        var productOpt = prodRepo.findById(request.getId());
        if (productOpt.isEmpty()){
            return false;
        }
        var product = productOpt.get();
        product.setPrice(request.getPrice());
        product.setSKU(request.getSKU());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setExpiryDate(request.getExpiryDate());
        prodRepo.save(product);
        return true;
    }

    /**
     * Deletes product by ID
     * @param id of the product to be deleted
     * @return boolean to signify successful deletion
     */

    @Override
    public boolean deleteProductById(long id) {
        prodRepo.deleteById(id);
        return true;
    }

    /**
     * Retrieves a paginated product response
     * @param search parameter to filter product by
     * @param category of product required
     * @param pageNo of paginated products
     * @param pageSize of paginated products
     * @return a paginated products list
     */
    @Override
    public PaginatedProductsResponse getAll(String search, List<ProductCategory> category, int pageNo, int pageSize) {
        if (pageNo < 0){
            pageNo = 1;
        }
        if (pageSize < 0 || pageSize > 100){
            pageSize = 10;
        }
        var pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Product> products = null;
        if (search != null && category != null){
            products = prodRepo.findAllByDescriptionContainingAndCategoryContaining(search, category, pageable);
        } else if (category != null){
            products = prodRepo.findAllByCategoryContaining(category,pageable);
        } else if (search != null){
            products = prodRepo.findAllByDescriptionContaining(search,pageable);
        } else {
            products = prodRepo.findAll(pageable);
        }

        var productsDetail = products.stream()
                .map(product -> mapper.map(product, ProductDetailsResponse.class))
                .toList();
        return PaginatedProductsResponse.builder()
                .products(productsDetail)
                .currentItems(products.getNumberOfElements())
                .currentPage(products.getNumber() + 1)
                .totalItems(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .build();
    }

    @Override
    public PaginatedProductsResponse getExpired() {
        var pageable = PageRequest.of(0, 10);
        Page<Product> products = prodRepo.getExpired(LocalDate.now(), pageable);
        var productsDetail = products.stream()
                .map(product -> mapper.map(product, ProductDetailsResponse.class))
                .toList();
        return PaginatedProductsResponse.builder()
                .products(productsDetail)
                .currentItems(products.getNumberOfElements())
                .currentPage(products.getNumber() + 1)
                .totalItems(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .build();
    }
}