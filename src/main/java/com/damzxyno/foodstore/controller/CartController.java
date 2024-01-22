package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.dto.request.cart.CartItemCreationDTO;
import com.damzxyno.foodstore.filter.AuthFilter;
import com.damzxyno.foodstore.service.interfaces.CartService;
import com.damzxyno.foodstore.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * This class helps with all cart related http request
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final AuthFilter filter;

    @PostMapping("/{custId}/{productId}/{quantity}")
    public String addToCart(@PathVariable Long custId, @PathVariable Long productId, @PathVariable int quantity, RedirectAttributes attributes){
        cartService.addCartItem(new CartItemCreationDTO(productId, custId, quantity));
        var product = productService.getProductById(productId);
        var message = String.format("Product %s added to the cart successfully!", product.get().getDescription());
        attributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }

    @GetMapping("/{custId}/{pageNo}")
    public String getCart(HttpServletRequest request, @PathVariable Long custId, @PathVariable int pageNo, Model model){
        var cart = cartService.getCartItemsForCustomer(custId, pageNo, 20);
        var loggedInUser = filter.parseLoginContextFromRequest(request);
        model.addAttribute("userId", loggedInUser.getId());
        model.addAttribute("cart", cart);
        var price = cart.getProducts()
                .stream().map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        model.addAttribute("itemPrice", price);
        model.addAttribute("subtotalPrice", price);
        model.addAttribute("totalPrice", price);
        return "cart";
    }

    @GetMapping("/clear/{customerId}")
    public String clearCart(@PathVariable Long customerId, RedirectAttributes attributes){
        attributes.addFlashAttribute("message", "Checkout successful!");
        cartService.deleteCartItems(customerId);
        return "redirect:/products";
    }
}