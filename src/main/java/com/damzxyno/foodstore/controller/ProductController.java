package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.dto.request.product.ProductCreationRequest;
import com.damzxyno.foodstore.dto.request.product.ProductModificationRequest;
import com.damzxyno.foodstore.enums.ProductCategory;
import com.damzxyno.foodstore.enums.UserType;
import com.damzxyno.foodstore.filter.AuthFilter;
import com.damzxyno.foodstore.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This class takes every product related requests.
 */
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final AuthFilter filter;

    /**
     * This method helps to display  products based on certain filter params
     */
    @GetMapping()
    public String getAllProducts(HttpServletRequest request,
                                 Model model,
                                 @RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) String category){
        var req = filter.parseLoginContextFromRequest(request);
        if (!req.isLoggedIn()){
            return "redirect:/";
        }
        model.addAttribute("isAdmin", req.getUserType().equals(UserType.ADMIN));
        model.addAttribute("userId", req.getId());
        model.addAttribute("productCategories", ProductCategory.values());
        var cat = category == null ? null :List.of(ProductCategory.valueOf(category));
        model.addAttribute("products", productService.getAll(search, cat, page, 6));

        return "products";
    }

    /**
     * This returns expired products
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/expired")
    public String getExpired(HttpServletRequest request,
                                 Model model){
        var req = filter.parseLoginContextFromRequest(request);
        if (!req.isLoggedIn()){
            return "redirect:/";
        }
        model.addAttribute("isAdmin", req.getUserType().equals(UserType.ADMIN));
        model.addAttribute("userId", req.getId());
        model.addAttribute("products", productService.getExpired());

        return "products";
    }


    /**
     * This method helps to display a product edit page
     */
    @GetMapping("/edit/{id}")
    public String editProduct(HttpServletRequest request, @PathVariable long id, Model model){
        var req = filter.parseLoginContextFromRequest(request);
        if (req == null){
            return "redirect:/";
        }
        if (!req.getUserType().equals(UserType.ADMIN)){
            return "no-permission";
        }
        var product = productService.getProductById(id);
        if (product.isEmpty()){
            return "404";
        }
        model.addAttribute("isAdmin", req.getUserType().equals(UserType.ADMIN));
        model.addAttribute("userId", req.getId());
        model.addAttribute("product", product.get());
        model.addAttribute("productCategories", ProductCategory.values());
        return "edit-product";
    }

    /**
     * This method helps to complete products edit
     */
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable long id, @ModelAttribute ProductModificationRequest request, RedirectAttributes attributes){
        request.setId(id);
        var product = productService.getProductById(id);
        var message = String.format("Product %s updated successfully!", product.get().getDescription());
        productService.modifyProduct(request);
        attributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }

    /**
     * This method helps to complete product delete
     */
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id, RedirectAttributes attributes){
        var product = productService.getProductById(id);
        var message = String.format("Product %s deleted successfully!", product.get().getDescription());
        productService.deleteProductById(id);
        attributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }


    /**
     * This method serves the product creation page
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String createProduct(HttpServletRequest request, Model model){
        model.addAttribute("product", new ProductCreationRequest());
        model.addAttribute("productCategories", ProductCategory.values());
        return "create-product";
    }

    /**
     * This method completes product creation
     * @param product
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductCreationRequest product, RedirectAttributes redirectAttributes){
        productService.createProduct(product);
        var message = String.format( "New product, %s added successfully", product.getDescription());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }
}
