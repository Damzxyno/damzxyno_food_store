package com.damzxyno.foodstore.dto.response.product;

import com.damzxyno.foodstore.dto.response.PaginatedResponse;
import com.damzxyno.foodstore.dto.response.cart.CartProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Paginated product response
 *
 */
@Getter
@Setter
public class PaginatedProductsResponse extends  PaginatedResponse{
        private List<ProductDetailsResponse> products;

        @Builder
        public PaginatedProductsResponse(long totalItems, long totalPages, long currentPage, long currentItems, List<ProductDetailsResponse> products){
            super(totalItems, totalPages, currentPage, currentItems);
            this.products = products;
        }

    }
