package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.dto.request.product.ProductModificationRequest;
import com.damzxyno.foodstore.enums.ProductCategory;
import com.damzxyno.foodstore.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * This class takes every product related requests.
 */
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping()
    public String getAllProducts(Model model,
                                 @RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false) String search){
        model.addAttribute("products", productService.getAll(search, null, page, 20));
        return "products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable long id, Model model){
        var product = productService.getProductById(id);
        if (product.isEmpty()){
            return "404";
        }
        model.addAttribute("product", product.get());
        model.addAttribute("productCategories", ProductCategory.values());
        return "edit-product";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable long id, @ModelAttribute ProductModificationRequest request){
        request.setId(id);
        var product = productService.modifyProduct(request);
        return "redirect:" + id;
    }

    @PostMapping("/delete/{id}")
    public String getAllProducts(@PathVariable long id){
        var product = productService.deleteProductById(id);
        return "redirect:/products";
    }
}
