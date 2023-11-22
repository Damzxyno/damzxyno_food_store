package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.service.interfaces.CustomerService;
import com.damzxyno.foodstore.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping("/product")
    public String getAllProducts(Model model){
        model.addAttribute("products", productService.getAll(null, null, 1, 20));
        return "admin-products";
    }

    @GetMapping("/product/edit/{id}")
    public String getAllProducts(@PathVariable long id, Model model){
        var product = productService.getProductById(id);
        if (product.isEmpty()){
            return "404";
        }
        model.addAttribute("product", product.get());
        return "edit-product";
    }

    @PostMapping("/product/delete/{id}")
    public String getAllProducts(@PathVariable long id){
        System.out.println("DELETETTTEDDDD. . . . . . . . .");
        var product = productService.deleteProductById(id);
        return "redirect:/product";
    }

    @GetMapping("/customer")
    public String getALlCustomers(Model model){
        var customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "admin-customers";
    }
}
