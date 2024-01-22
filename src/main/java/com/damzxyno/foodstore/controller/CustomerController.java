package com.damzxyno.foodstore.controller;

import com.damzxyno.foodstore.enums.UserType;
import com.damzxyno.foodstore.filter.AuthFilter;
import com.damzxyno.foodstore.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * This class takes every customer related requests.
 */
@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AuthFilter filter;

    /**
     * This methods display all customers
     * @param model
     * @param page
     * @return
     */
    @GetMapping()
    public String getAllCustomers(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "1") int page){
        var req = filter.parseLoginContextFromRequest(request);
        model.addAttribute("isAdmin", req.getUserType().equals(UserType.ADMIN));
        model.addAttribute("userId", req.getId());
        var customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customers";
    }


    /**
     * This method deletes customer from the database
     * @return
     */
    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(RedirectAttributes attributes, @PathVariable long customerId){
        attributes.addFlashAttribute("message", "Customer deleted successfully!");
        customerService.deleteCustomerByID(customerId);
        return "redirect:/customers";
    }

}
