package com.damzxyno.foodstore.service.interfaces;

import com.damzxyno.foodstore.dto.request.cart.CartItemCreationDTO;
import com.damzxyno.foodstore.dto.response.cart.PaginatedCartItemResponse;

public interface CartService {
    boolean addCartItem (CartItemCreationDTO request);
    PaginatedCartItemResponse getCartItemsForCustomer(Long custId, int pageNum, int pageSize);

    boolean deleteCartItems(long userId);

    boolean removeItemFromCart(long userId, long productId);
}
