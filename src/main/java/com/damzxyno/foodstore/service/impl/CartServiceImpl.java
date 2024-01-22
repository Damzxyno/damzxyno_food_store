package com.damzxyno.foodstore.service.impl;

import com.damzxyno.foodstore.dto.request.cart.CartItemCreationDTO;
import com.damzxyno.foodstore.dto.response.cart.PaginatedCartItemResponse;
import com.damzxyno.foodstore.dto.response.cart.CartProductResponse;
import com.damzxyno.foodstore.entity.Cart;
import com.damzxyno.foodstore.repository.CartRepository;
import com.damzxyno.foodstore.repository.ProductRepository;
import com.damzxyno.foodstore.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This cart services does all cart related implementations
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepo;
    private final ProductRepository prodRepo;
    private final ModelMapper mapper;


    /**
     * This method adds a new item to a customer's cart
     * @param request The cart creation request
     * @return a boolean to confirm cart creation
     */
    @Override
    public boolean addCartItem (CartItemCreationDTO request) {
        var cart = cartRepo.getCartByCustomerIdAndProductId(request.getCustomerId(), request.getProductId())
                .orElse(Cart.builder()
                        .customerId(request.getCustomerId())
                        .productId(request.getProductId())
                        .build());
        cart.setQuantity(cart.getQuantity() + request.getQuantity());
        cartRepo.save(cart);
        return true;
    }

    /**
     * Returns a paginated contents of a customer's cart.
     * @param custId The identifier for the customer
     * @param pageNum The paginated number
     * @param pageSize The paginated size
     * @return the a paginated cart item response
     */

    @Override
    public PaginatedCartItemResponse getCartItemsForCustomer(Long custId, int pageNum, int pageSize) {
        var pagingRequest = PageRequest.of(pageNum - 1,pageSize);
        var carts = cartRepo.getProductsByCustomerId(custId, pagingRequest);
        var productsRes = carts.stream()
                .map(cart -> {
                    var cartProductResponse = mapper.map(cart.getProduct(), CartProductResponse.class);
                    cartProductResponse.setQuantity(cart.getQuantity());
                    return cartProductResponse;
                })
                .toList();
        return PaginatedCartItemResponse.builder()
                .products(productsRes)
                .currentItems(carts.getNumberOfElements())
                .currentPage(carts.getNumber() + 1)
                .totalItems(carts.getTotalElements())
                .totalPages(carts.getTotalPages())
                .build();

    }

    /**
     * Deletes all content of a customer's cart
     * @param customerId identifier for customer
     * @return a boolean to signify successful deletion
     */

    @Override
    @Transactional
    public boolean deleteCartItems(long customerId) {
        cartRepo.deleteAllByCustomerId(customerId);
        return true;
    }


    /**
     * Removes a single product from the customer's cart
     * @param customerId identifier for a customer
     * @param productId identifier for the product to be deleted
     * @return a boolean to confirm successful product deletion
     */
    @Override
    @Transactional
    public boolean removeItemFromCart(long customerId, long productId) {
        cartRepo.deleteCartByCustomerIdAndProductId(customerId, productId);
        return true;
    }
}