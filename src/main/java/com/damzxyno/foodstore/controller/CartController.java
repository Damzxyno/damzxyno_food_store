package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.dto.request.cart.CartItemCreationDTO;
import com.damzxyno.foodstore.dto.request.product.ProductCreationRequest;
import com.damzxyno.foodstore.dto.response.cart.PaginatedCartItemResponse;
import com.damzxyno.foodstore.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{custId}")
    public ResponseEntity<PaginatedCartItemResponse> getAllCartItems(
            @PathVariable(name = "custId") Long custId,
            @RequestParam(name = "pageNo", required = false) int pageNo,
            @RequestParam(name = "pageSize", required = false) int pageSize){
        var res = cartService.getCartItemsForCustomer(custId, pageNo, pageSize);
        return ResponseEntity.ok(res);
    }

//    @GetMapping("{id}")
//    public ResponseEntity<Product> getCartItemById(@PathVariable(name = "id") Long id){
//        return null;
//    }

    @PostMapping
    public ResponseEntity<Boolean> addItemToCart(@RequestBody CartItemCreationDTO request){
        var res = cartService.addCartItem(request);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> modifyCartItemById(@PathVariable(name = "id") Long id, @RequestBody ProductCreationRequest request){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCartItemById(){
        return null;
    }
}
