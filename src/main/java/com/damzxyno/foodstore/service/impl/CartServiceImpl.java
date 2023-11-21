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

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepo;
    private final ProductRepository prodRepo;
    private final ModelMapper mapper;


    @Override
    public boolean addCartItem (CartItemCreationDTO request) {
        var existed = cartRepo.existsByCustomerIdAndProductId(request.getCustomerId(), request.getProductId());
        if (existed){
            System.out.println("PRODUCT EXIST IN CART!");
        }
        var cart = Cart.builder()
                .customerId(request.getCustomerId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
        var res = cartRepo.save(cart);
        return true;
    }

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

    @Override
    @Transactional
    public boolean deleteCartItems(long userId) {
        cartRepo.deleteAllByCustomerId(userId);
        return true;
    }

    @Override
    @Transactional
    public boolean removeItemFromCart(long userId, long productId) {
        cartRepo.deleteCartByCustomerIdAndProductId(userId, productId);
        return true;
    }
}