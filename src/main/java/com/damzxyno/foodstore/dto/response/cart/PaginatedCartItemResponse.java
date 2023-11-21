package com.damzxyno.foodstore.dto.response.cart;

import com.damzxyno.foodstore.dto.response.PaginatedResponse;
import com.damzxyno.foodstore.dto.response.product.ProductDetailsResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class PaginatedCartItemResponse extends PaginatedResponse {
    private List<CartProductResponse> products;

    @Builder
    public PaginatedCartItemResponse(long totalItems, long totalPages, long currentPage, long currentItems, List<CartProductResponse> products){
        super(totalItems, totalPages, currentPage, currentItems);
        this.products = products;
    }
}
