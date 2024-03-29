package com.damzxyno.foodstore.console_ui;

import com.damzxyno.foodstore.console_ui.enums.Colour;
import com.damzxyno.foodstore.console_ui.helpers.ConsoleIOManager;
import com.damzxyno.foodstore.console_ui.statics.Message;
import com.damzxyno.foodstore.dto.request.cart.CartItemCreationDTO;
import com.damzxyno.foodstore.dto.request.customer.CustomerCreationRequest;
import com.damzxyno.foodstore.dto.request.identity.LoginRequest;
import com.damzxyno.foodstore.dto.request.product.ProductCreationRequest;
import com.damzxyno.foodstore.dto.request.product.ProductModificationRequest;
import com.damzxyno.foodstore.dto.response.cart.CartProductResponse;
import com.damzxyno.foodstore.dto.response.customer.CustomerDetailsResponse;
import com.damzxyno.foodstore.dto.response.product.PaginatedProductsResponse;
import com.damzxyno.foodstore.dto.response.product.ProductDetailsResponse;
import com.damzxyno.foodstore.enums.ProductCategory;
import com.damzxyno.foodstore.enums.UserType;
import com.damzxyno.foodstore.service.interfaces.CartService;
import com.damzxyno.foodstore.service.interfaces.CustomerService;
import com.damzxyno.foodstore.service.interfaces.IdentityService;
import com.damzxyno.foodstore.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.damzxyno.foodstore.console_ui.statics.Message.*;

/**
 * This class runs the console app
 */

@Component
@RequiredArgsConstructor
public class ConsoleApp {
    /**
     * This stores the info/ meta-data of the currently logged-in user.
     */
    private static SessionData sessionData;
    
    private final IdentityService identityService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartService cartService;
    private final NumberFormat gbpFormatter;
    private final ConsoleIOManager io;
    

    /**
     * This is the entrypoint into the ConsoleApp Application
     */
    public void run(){
        loginAction(3, false);
        if (sessionData.getUserType().equals(UserType.ADMIN)){
            adminOptionsAction();
        } else {
            customerOptionsAction();
        }
    }

    /**
     * This is the entrypoint into the customer dashboard and operations
     */
    private void customerOptionsAction() {
        var back = false;
        io.write("\n");
        customerWelcomeAction();
        io.write(CUSTOMER_DASHBOARD_OPTIONS);
        io.write(Colour.CYAN, PICK_AN_OPTION);
        io.write(DELIMETER_II);
        var option = io.readNumber(4, 1, 4, false);
        switch (option){
            case 1 -> {
                customerListAllProductsAction(1, false, 1);
                customerOptionsAction();
            }
            case 2 -> {
                customerViewProductByIdAction(1, false);
                customerOptionsAction();
            }
            case 3 -> {
                customerViewProductByKeyWordAction(1, false, null, 0);
                customerOptionsAction();
            }
            case 4 -> {
                viewCustomerCartAction(1, false, 1);
                customerOptionsAction();
            }
            case 5 -> back = true;
        }
        if (back){
            return;
        } else {
            customerOptionsAction();
        }
    }

    /**
     * This method help display all the items of a customer's cart
     * @param noOfRemainingAttempts
     * @param promptRemainingAttempts
     * @param pageNum
     */
    private void viewCustomerCartAction (int noOfRemainingAttempts, boolean promptRemainingAttempts, int pageNum){
        var cartItems = cartService.getCartItemsForCustomer(sessionData.getUserId(), pageNum, 20);
        if (cartItems.getTotalItems() == 0){
            io.writeWarning("You have an empty cart!");
            return;
        }
        io.write("\n");
        io.write(Message.DELIMETER_I);
        io.write("::WELCOME TO YOUR CART, (%s)::", sessionData.getUsername());
        io.write(Message.DELIMETER_I);
        io.write(formatCartProducts(cartItems.getProducts()));
        io.write(String.format(PRODUCT_META_INFO,
                cartItems.getCurrentPage(),
                cartItems.getTotalPages(),
                cartItems.getTotalItems()));
        io.write(String.format("[1] Previous Page \t[2] Next Page \t[3]Remove item \t[4] Checkout \t[0] Exit"));
        var option = io.readNumber(3, 1, 4, false);
        switch (option){
            case 1 -> {
                if (pageNum - 1 < 1){
                    io.writeWarning(PAGE_SIZE_EXCEEDED);
                    viewCustomerCartAction(noOfRemainingAttempts, promptRemainingAttempts, pageNum);
                    return;
                }
                viewCustomerCartAction(3, false, pageNum - 1);
            }
            case 2 -> {
                if (pageNum + 1 > cartItems.getTotalPages()) {
                    io.writeWarning("Page size exceeded");
                    viewCustomerCartAction(noOfRemainingAttempts, promptRemainingAttempts, pageNum);
                    return;
                }
                viewCustomerCartAction(3, false, pageNum + 1);
            }
            case 3 -> removeCustomerCartItemAction(0);
            case 4 -> customerCheckoutAction();
        }
    }

    /**
     * This method help remove an item away from the customer's cart
     * @param productId is the id of the product to be removed.
     */
    private void removeCustomerCartItemAction(long productId) {
        if (productId == 0){
            io.write("What is the product Id?");
            productId = io.readNumber(3, 1, Integer.MAX_VALUE, false);
        }
        var res = cartService.removeItemFromCart(sessionData.getUserId(), productId);
        io.writeSuccess(ITEM_REMOVED_FROM_CART_SUCCESS);
    }

    /**
     * This method help checkout a customer's cart
     */
    private void customerCheckoutAction() {
        var cartPrice = cartService.getCartItemsForCustomer(sessionData.getUserId(), 1, 99)
                .getProducts()
                .stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        var gbpPrice = gbpFormatter.format(cartPrice);
        cartService.deleteCartItems(sessionData.getUserId());
        io.writeSuccess("Cart cleared successfully.");
        io.writeSuccess("You've been debited the sum of %s", gbpPrice);
        io.writeSuccess("Your goods are on it's way!");
    }

    /**
     * This method helps find and display product by Id to the customer.
     */
    private void customerViewProductByIdAction(int noOfRemainingAttempts, boolean promptRemainingAttempts){
        io.write("Please, input a valid product Id");
        var id = io.readNumber(5, 1, Integer.MAX_VALUE, false);
        var product = productService.getProductById((long) id);
        if (product.isEmpty()){
            io.write(INVALID_OPTION);
            io.writeWarning("Product with id %d doesn't exist!");
            customerViewProductByIdAction(noOfRemainingAttempts -1, true);
            return;
        }
        io.write(formatProducts(List.of(product.get())));
        io.write(CUSTOMER_SINGLE_PRODUCT_OPTION);
        var option = io.readNumber(3, 1, 5, false);
        switch (option){
            case 1 -> {
                customerAddToCartAction(product.get().getId());
            }
            case 2 -> {
                removeCustomerCartItemAction(product.get().getId());
            }
        }
    }

    /**
     * This method helps find and display product by keyword to the customer.
     */
    private void customerViewProductByKeyWordAction(int noOfRemainingAttempts, boolean promptRemainingAttempts, String keyword, int pageNum){
        PaginatedProductsResponse product = null;
        if (pageNum == 0) {
            io.write("Please, input a keyword");
            keyword = io.readString(5, false);
            product = productService.getAll(keyword, null, 1, 20);
            if (product.getTotalItems() == 0){
                io.write(INVALID_OPTION);
                io.writeWarning("Product with search keyword doesn't exist!");
                customerViewProductByIdAction(noOfRemainingAttempts -1, true);
                return;
            }
        } else {
            product = productService.getAll(keyword, null, pageNum, 20);
        }

        io.write(formatProducts(product.getProducts()));
        io.write(CUSTOMER_PRODUCT_SEARCH_OPTION);
        var option = io.readNumber(3, 1, 5, false);
        switch (option){
            case 1 -> {
                if (pageNum - 1 < 1){
                    io.writeWarning(PAGE_SIZE_EXCEEDED);
                    customerViewProductByKeyWordAction(noOfRemainingAttempts, promptRemainingAttempts, keyword, pageNum);
                    return;
                }
                customerViewProductByKeyWordAction(3, false, keyword, pageNum - 1);
            }
            case 2 -> {
                if (pageNum + 1> product.getTotalPages()){
                    io.writeWarning("Page size exceeded");
                    customerViewProductByKeyWordAction(noOfRemainingAttempts, promptRemainingAttempts,keyword, pageNum);
                    return;
                }
                customerViewProductByKeyWordAction(3, false, keyword, pageNum + 1);
            }
            case 3 -> {
                customerAddToCartAction(0);
                customerViewProductByKeyWordAction(3, false, keyword, pageNum);
            }
            case 4 -> viewCustomerCartAction(3, false, 1);
        }
    }


    /**
     * This is the entrypoint into the admin dashboard and operations
     */
    private void adminOptionsAction() {
        adminWelcomeAction();
        io.write(ADMIN_DASHBOARD_OPTIONS);
        var option = io.readNumber(4, 1, 6, false);
        var back = false;
        switch (option) {
            case 1 -> adminProductDashboardAction();
            case 2 -> adminCustomerManagementDashboard();
            case 3 -> back = true;
        }
        if (back){
            run();
        } else {
            adminOptionsAction();
        }
    }

    /**
     * This method presents the admin's dashboard
     */
    private void adminCustomerManagementDashboard() {
        io.write(Message.DELIMETER_I);
        io.write(ADMIN_CUSTOMER_MANAGEMENT_DASHBOARD_MESSAGE, sessionData.getUsername());
        io.write(Message.DELIMETER_I);
        io.write(ADMIN_CUSTOMER_DASHBOARD_OPTIONS);
        var option = io.readNumber(3, 1, 3, false);
        var back = false;
        switch (option){
            case 1 -> adminViewAllCustomerAction();
            case 2 -> adminViewCustomerByIdAction();
            case 3 -> back = true;
        }
        if (back){
            adminOptionsAction();
        } else {
            adminCustomerManagementDashboard();
        }
    }


    /**
     * This method help admin view customer details by id
     */
    private void adminViewCustomerByIdAction() {
        io.write("Input customer's Id");
        var input = io.readNumber(3, 1, Integer.MAX_VALUE, false);
        var customer = customerService.getCustomerById((long) input);
        if (customer.isEmpty()){
            io.writeError("Customer with Id does not exist!");
            return;
        }
        var formatCustomer = formatCustomer(List.of(customer.get()));
        io.write(formatCustomer);
    }

    /**
     * This method helps admin to view all customers
     */
    private void adminViewAllCustomerAction() {
        var customers = customerService.getAll();
        var formatCustomer = formatCustomer(customers);
        io.write(formatCustomer);
    }

    /**
     * This method helps admin to view all products
     */
    private void adminProductDashboardAction() {
        io.write(Message.DELIMETER_I);
        io.write(ADMIN_PRODUCT_MANAGEMENT_DASHBOARD_MESSAGE, sessionData.getUsername());
        io.write(Message.DELIMETER_I);
        io.write(ADMIN_PRODUCT_DASHBOARD_OPTIONS);
        var option = io.readNumber(3, 1, 6, false);
        boolean back = false;
        switch (option){
            case 1 -> adminListAllProducts(1, false, 1);
            case 2 -> adminViewProductById();
            case 3 -> adminEditProductByIdAction(0);
            case 4 -> adminDeleteProductByIdAction(0);
            case 5 -> adminAddNewProductAction();
            case 6 -> back = true;
        }
        if (back){
            adminCustomerManagementDashboard();
        } else {
            adminProductDashboardAction();
        }
    }

    /**
     * This method helps admin delete products
     * @param productId
     */
    private void adminDeleteProductByIdAction(long productId) {
        io.write(Colour.CYAN,DELETE_PRODUCT_PROMPT);
        if (productId == 0){
            io.write("What is the product Id?");
            productId = io.readNumber(3, 1, Integer.MAX_VALUE, false);
        }
        var deleted = productService.deleteProductById(productId);
        io.writeSuccess("Product deleted successfully!");
    }

    /**
     * This method helps to map numbers to product category for easy processing
     * @return a map linking numbers to specific product category
     */

    private HashMap<Integer, ProductCategory> mapCategory(){
        var result = new HashMap<Integer, ProductCategory>();
        var index = 2;
        for (var item : ProductCategory.values()){
            result.put(index, item);
            index++;
        }
        return result;
    }

    /**
     * This method return a string to display numbers and their mapped product category
     * @param map
     * @return
     */
    private String sortCategory(Map<Integer, ProductCategory> map){
        var sb = new StringBuilder();
        sb.append("[1] None/ Remain unchanged");
        for(int i = 2; i < map.size(); i++){
            sb.append(String.format("\t[%d] %s", i, map.get(i).toString()));
        }
        return sb.toString();
    }
    private void adminEditProductByIdAction (long productId) {
        if (productId == 0){
            io.write("What is the product Id?");
            productId = io.readNumber(3, 1, Integer.MAX_VALUE, false);
        }
        io.write(Colour.CYAN,"Put the value 'NONE' to have it's previous parameter unchanged.");
        io.write(ADMIN_PRODUCT_DESCRIPTION_REQUEST);
        var description = io.readString(3, false);
        Map<Integer, ProductCategory> categoryMap = mapCategory();
        io.write(ADMIN_PRODUCT_CATEGORY_REQUEST);
        io.write(sortCategory(categoryMap));
        var category = io.readNumber(3, 1, mapCategory().size() + 1, false);
        io.write(ADMIN_PRODUCT_PRICE_REQUEST);
        io.write(Colour.CYAN, "Input 0.0 if unchanged?");
        var price = io.readString(5, false);
        var entryPrice = price.equals("0.0")  || price.equalsIgnoreCase("none")? null : BigDecimal.valueOf(Double.parseDouble(price));
        var request = ProductModificationRequest.builder()
                .id(productId)
                .description(description.equalsIgnoreCase("none") ? null : description)
                .category(category ==  1 ? null : categoryMap.get(category))
                .price(entryPrice)
                .SKU("SKU")
                .build();
        productService.modifyProduct(request);
        io.writeSuccess(ADMIN_SUCCESSFUL_PRODUCT_CREATION_PROMPT);
    }

    /**
     * This method helps admin view a product by Id
     */
    private void adminViewProductById() {
        io.write("Please, input a valid product Id");
        var id = io.readNumber(5, 1, Integer.MAX_VALUE, false);
        var product = productService.getProductById((long) id);
        if (product.isEmpty()){
            io.write(INVALID_OPTION);
            io.writeWarning("Product with id %d doesn't exist!");
            adminViewProductById();
            return;
        }
        io.write(formatProducts(List.of(product.get())));
        io.write(ADMIN_SINGLE_PRODUCT_OPTION);
        var option = io.readNumber(3, 1, 5, false);
        switch (option){
            case 1 -> adminEditProductByIdAction(product.get().getId());
            case 2 -> adminDeleteProductByIdAction(product.get().getId());
        }
    }

    /**
     * This method displays all existing products
     * @param noOfRemainingAttempts
     * @param promptRemainingAttempts
     */
    private void customerListAllProductsAction(int noOfRemainingAttempts, boolean promptRemainingAttempts, int pageNum){
        var back = false;
        var pageSize = 8;
        var products = productService.getAll(null, null, pageNum, 20);
        if (products.getTotalItems() == 0){
            io.writeWarning("There seem to be no products currently, please try again later!");
            System.exit(0);
        }
        io.write(DELIMETER_II);
        io.write(formatProducts(products.getProducts()));
        io.write(String.format("[CURRENT PAGE NO: %s] :: [TOTAL PAGES: %s] - [TOTAL PRODUCTS: %s]",
                products.getCurrentPage(),
                products.getTotalPages(),
                products.getTotalItems()));
        io.write(CUSTOMER_PRODUCT_OPTION);
        var option = io.readNumber(3, 1, 5, false);
        switch (option){
            case 1 -> {
                if (pageNum - 1 < 1){
                    io.writeWarning(PAGE_SIZE_EXCEEDED);
                    customerListAllProductsAction(noOfRemainingAttempts, promptRemainingAttempts, pageNum);
                    return;
                }
                customerListAllProductsAction(3, false, pageNum - 1);
            }
            case 2 -> {
                if (pageNum + 1> products.getTotalPages()){
                    io.writeWarning("Page size exceeded");
                    customerListAllProductsAction(noOfRemainingAttempts, promptRemainingAttempts, pageNum);
                    return;
                }
                customerListAllProductsAction(3, false, pageNum + 1);
            }
            case 3 -> {
                customerAddToCartAction(0);
                customerListAllProductsAction(3, false, pageNum);
            }
            case 4 -> viewCustomerCartAction(3, false, 1);
            case 5 -> back = true;
        }
        if(back){
            return;
        } else {
            customerListAllProductsAction(noOfRemainingAttempts, promptRemainingAttempts, pageNum);
        }
    }

     /**
     * This method displays all existing products for Admin
     * @param noOfRemainingAttempts
     * @param promptRemainingAttempts
     */
    private void adminListAllProducts(int noOfRemainingAttempts, boolean promptRemainingAttempts, int pageNum){
        var pageSize = 6;
        var products = productService.getAll(null, null, pageNum, pageSize);
        if (products.getTotalItems() == 0){
            io.writeWarning(ADMIN_NO_PRODUCT_ADD_PRODUCT_PROMPT);
            adminAddNewProductAction();
        }
        io.write(formatProducts(products.getProducts()));
        io.write(Colour.CYAN, PRODUCT_META_INFO,
                products.getCurrentPage(),
                products.getTotalPages(),
                products.getTotalItems()
                );
        io.write(ADMIN_PRODUCT_OPTION);
        var option = io.readNumber(3, 1, 5, false);
        var back = false;
        switch (option){
            case 1 -> {
                if (pageNum - 1 < 1){
                    io.writeWarning(PAGE_SIZE_EXCEEDED);
                    adminListAllProducts(noOfRemainingAttempts - 1, false, pageNum);
                }
                adminListAllProducts(3, false, pageNum - 1);
            }
            case 2 -> {
                if (pageNum + 1> products.getTotalPages()){
                    io.writeWarning(PAGE_SIZE_EXCEEDED);
                    adminListAllProducts(noOfRemainingAttempts, false, pageNum);
                }
                adminListAllProducts(3, false, pageNum + 1);
            }
            case 3 -> {
                adminDeleteProductByIdAction(0);
            }
            case 4 -> {
                adminEditProductByIdAction(0);
            }
            case 5 -> back = true;
        }
        if(back){
            adminProductDashboardAction();
        } else {
            adminListAllProducts(3, false, 1);
        }
    }

    /**
     * This method helps admin to create new products.
     */
    private void adminAddNewProductAction() {
        io.write(ADMIN_PRODUCT_DESCRIPTION_REQUEST);
        var description = io.readString(3, false);
        Map<Integer, ProductCategory> categoryMap = mapCategory();
        io.write(sortCategory(categoryMap));
        io.write(ADMIN_PRODUCT_CATEGORY_REQUEST);
        var category = io.readNumber(4, 1, mapCategory().size() + 1, false);
        io.write(ADMIN_PRODUCT_PRICE_REQUEST);
        var price = io.readString(5, false);
        var request = ProductCreationRequest.builder()
                .description(description)
                .category(categoryMap.get(category))
                .price(BigDecimal.valueOf(Double.parseDouble(price)))
                .SKU("SKU")
                .build();
        productService.createProduct(request);
        io.writeSuccess(ADMIN_SUCCESSFUL_PRODUCT_CREATION_PROMPT);
    }

    /**
     * This function adds product to Customer's Cart!
     */
    private void customerAddToCartAction(long productId){
        if (productId == 0){
            io.write("What is the product Id?");
            productId = io.readNumber(3, 1, Integer.MAX_VALUE, false);
        }
        io.write("How many of this product do you want?");
        var quantity = io.readNumber(3, 1, Integer.MAX_VALUE, false);

        var res = cartService.addCartItem(CartItemCreationDTO.builder()
                .customerId(sessionData.getUserId())
                .productId(productId)
                .quantity(quantity)
                .build());
        io.writeSuccess(ITEM_ADDED_TO_CART_SUCCESS);

    }


    /**
     * This method formats the products list into a table.
     * @param products This is a list of all products that should be contained in the table.
     * @return a string witht eh products well formatted.
     */
    private String formatProducts(List<ProductDetailsResponse> products){
        var format = "%-4s | %-14s | %-20s | %-10s | %-10s%n";
        var hFormat = "%-4s | %-14s | %-20s | %-10s | %-10s%n";
        var solution = new StringBuilder();
        solution.append(DELIMETER_I +"\n");
        solution.append("===PRODUCT LIST===\n");
        solution.append(DELIMETER_I + "\n");
        solution.append(String.format(hFormat, "ID", "SKU", "Description", "Category", "Price"));
        for(int i = 0; i < products.size(); i++) {
            var product = products.get(i);
            solution.append(String.format(format,
                    product.getId(),
                    product.getSKU(),
                    product.getDescription(),
                    product.getCategory(),
                    gbpFormatter.format(product.getPrice())));
        }
        return solution.toString().trim();
    }

    /**
     * This method formats the products list into a table.
     * @param customers This is a list of all products that should be contained in the table.
     * @return a string with the products well formatted.
     */
    private String formatCustomer(List<CustomerDetailsResponse> customers){
        var format = "%d| %-5s\n";
        var hFormat = "%s| %-5s\n";
        var solution = new StringBuilder();
        solution.append(DELIMETER_I +"\n");
        solution.append("===PRODUCT LIST===\n");
        solution.append(DELIMETER_I + "\n");
        solution.append(String.format(hFormat, "NO", "Username"));
        for(int i = 0; i < customers.size(); i++) {
            var customer = customers.get(i);
            solution.append(String.format(format,
                    i + 1,
                    customer.getUsername()));
        }
        return solution.toString().trim();
    }


    /**
     * This helps format the products in a cart to the screen
     * @param products
     * @return
     */
    private String formatCartProducts(List<CartProductResponse> products){
        var format = "%-4d| %-4s | %-10s | %-20s | %-15s | %-10s | %-20s | %-15s%n";
        var hformat = "%-4s| %-4s | %-10s | %-20s | %-15s | %-10s | %-20s | %-15s%n";
        var solution = new StringBuilder();
        solution.append(String.format(hformat, "NO", "ID", "SKU", "Description", "Category", "Unit Price", "Quantity", "Total Price"));
        for(int i = 0; i < products.size(); i++) {
            var product = products.get(i);
            solution.append(String.format(format,
                    i + 1,
                    product.getSKU(),
                    product.getId(),
                    product.getDescription(),
                    product.getCategory(),
                    gbpFormatter.format(product.getPrice()),
                    product.getQuantity(),
                    gbpFormatter.format(product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))));
        }
        solution.append("GRAND TOTAL =================== " +
                gbpFormatter.format(
                        products.stream()
                                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)));
        return solution.toString();
    }


    /**
     * This is prints a customer welcome message to the screen
     */
    private void customerWelcomeAction(){
        io.write(Message.DELIMETER_I);
        io.write(CUSTOMER_DASHBOARD_MESSAGE, sessionData.getUsername());
        io.write(Message.DELIMETER_I);
    }

    /**
     * This is prints an admin welcome message to the screen
     */
    private void adminWelcomeAction(){
        io.write(Message.DELIMETER_I);
        io.write(Message.ADMIN_DASHBOARD_MESSAGE, sessionData.getUsername());
        io.write(Message.DELIMETER_I);
    }

    /**
     * This method helps to log a user(ADMIN/ CUSTOMER) in, as well as creation of account for customers.
     * @param noOfRemainingAttempts keeps tract of how many login attempts the user has.
     * @param promptRemainingAttempts check if the user should be prompted how many login attempts remains.
     */
    private void loginAction(int noOfRemainingAttempts, boolean promptRemainingAttempts){
        if (noOfRemainingAttempts == 0){
            io.writeError(LOGIN_ATTEMPT_EXHAUSTION);
            System.exit(0);
        }
        if (promptRemainingAttempts){
            io.write(Colour.RED, LOGIN_ATTEMPT_REMAINING_WARNING, noOfRemainingAttempts);
        }
        io.write(Message.DELIMETER_I);
        io.write(Message.WELCOME_MESSAGE);
        io.write(Message.DELIMETER_I);
        io.write(LOGIN_PROMPT);
        io.write(LOGIN_OPTIONS);
        io.write(Colour.CYAN, PICK_AN_OPTION);
        io.write(DELIMETER_II);
        var loginOption = io.readNumber(3, 1, 3, false);
        if (loginOption == 3){
            createCustomerAction(3, false);
            return;
        }
        io.write(Colour.CYAN, LOGIN_IN_AS_SIGNIFIER, loginOption == 1 ? "Administrator" : "Customer");
        io.write(LOGIN_INPUT_USERNAME_PROMPT);
        var username = io.readString(3, false);
        io.write(LOGIN_INPUT_PASSWORD_PROMPT);
        var password = io.readString(3, false);
        var userType = switch (loginOption){
            case 1 -> UserType.ADMIN;
            default -> UserType.CUSTOMER;
        };
        var loginResponse = identityService.login(
                LoginRequest.builder()
                        .username(username)
                        .password(password)
                        .userType(userType)
                        .build());
        if (loginResponse.isSuccess()){
            sessionData = SessionData.builder()
                    .userId(loginResponse.getId())
                    .Username(loginResponse.getUsername())
                    .userType(loginResponse.getUserType())
                    .build();
            io.write(loginResponse.getUserType().toString());
            io.writeSuccess("SUCCESSFULLY LOGGED IN");
            return;
        }
        io.writeWarning(loginResponse.getMessage());
        loginAction(noOfRemainingAttempts- 1, true);
    }

    /**
     * This method helps to create accounts for customers
     * @param noOfRemainingAttempts This keeps tract of how many account creation attempts the user has.
     * @param promptRemainingAttempts This check if the user should be prompted how many account creation attempts remains.
     */
    private void createCustomerAction(int noOfRemainingAttempts, boolean promptRemainingAttempts) {
        if (noOfRemainingAttempts == 0){
            io.write(Colour.RED, CUSTOMER_ACCOUNT_CREATION_ATTEMPT_EXHAUSTION);
            System.exit(0);
        }
        io.write(DELIMETER_I);
        io.write(ACCOUNT_CREATION_MESSAGE);
        io.write(DELIMETER_I);
        if (promptRemainingAttempts){
            io.write(Colour.RED, CUSTOMER_ACCOUNT_CREATION_ATTEMPT_REMAINING_WARNING, noOfRemainingAttempts);
        }
        io.write(ACCOUNT_CREATION_INPUT_USERNAME_PROMPT);
        var username = io.readString(3, false);
        io.write(ACCOUNT_CREATION_INPUT_PASSWORD_PROMPT);
        var password = io.readString(3, false);
        var response = customerService.createCustomer(CustomerCreationRequest.builder()
                        .username(username)
                        .password(password)
                        .build());
        if (response > 0){
            io.writeSuccess(ACCOUNT_CREATION_SUCCESSFUL);
            io.write(Colour.CYAN, DELIMETER_II);
            loginAction(3, false);
            return;
        }
        io.writeWarning(ACCOUNT_CREATION_FAILURE);
        createCustomerAction(noOfRemainingAttempts- 1, true);
    }

}
