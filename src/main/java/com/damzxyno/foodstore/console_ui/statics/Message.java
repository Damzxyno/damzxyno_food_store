package com.damzxyno.foodstore.console_ui.statics;


/**
 * This class contains all the message templates for the console ui
 */
public final class Message {
    public static final String WELCOME_MESSAGE = "WELCOME TO DAMZXYNO FOOD STORE!";
    public static final String ACCOUNT_CREATION_MESSAGE = "WELCOME TO CUSTOMER ACCOUNT CREATION!";
    public static final String CUSTOMER_DASHBOARD_MESSAGE = "WELCOME TO CUSTOMER SECTION (%s)!";
    public static final String ADMIN_DASHBOARD_MESSAGE = "WELCOME TO ADMIN SECTION (%s)!";
    public static final String ADMIN_PRODUCT_MANAGEMENT_DASHBOARD_MESSAGE = "WELCOME TO PRODUCT MANAGEMENT SECTION (%s)!";
    public static final String ADMIN_CUSTOMER_MANAGEMENT_DASHBOARD_MESSAGE = "WELCOME TO CUSTOMER MANAGEMENT SECTION (%s)!";
    public static final String DELIMETER_I = "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%";
    public static final String DELIMETER_II = "-------------------------";
    public static final String LOGIN_PROMPT = "Choose a login option!";
    public static final String LOGIN_IN_AS_SIGNIFIER = "Logging you in as %s.";
    public static final String ACCOUNT_CREATION_FAILURE = "Account creation not successful, please try again later!";
    public static final String LOGIN_OPTIONS = "[1] login as an ADMIN \n[2] login as a Cusomer \n[3] Create account as a Customer \n[0] Exit";
    public static final String PICK_AN_OPTION = "\u001B[3mPlease, pick an option.\u001B[0m";
    public static final String CUSTOMER_DASHBOARD_OPTIONS = "[1] List all products \n[2] Search for product by ID \n[3] Search for product by keyword \n[4] View cart \n [5] Back \n[0] Exit";
    public static final String CUSTOMER_PRODUCT_OPTION = "[1] Previous Page \t [2] Next Page \t [3] Add to cart \t[4] View Cart/ Checkout \t[5] Back \t[0] Exit";
    public static final String CUSTOMER_PRODUCT_SEARCH_OPTION = "[1] Previous Page \t [2] Next Page \t [3] Add to cart \t[4] View Cart/ Checkout \t[5] Back \t[0] Exit";
    public static final String CUSTOMER_SINGLE_PRODUCT_OPTION = "[1] Add product to cart \t [2] View cart \t [3] Remove product from cart \t[4] Checkout \t[0] Exit";
    public static final String DELETE_PRODUCT_PROMPT = "Deleting a product.";
    public static final String ADMIN_SINGLE_PRODUCT_OPTION = "[1] Edit product \t [2] Delete product \t[0] Exit";
    public static final String ADMIN_PRODUCT_OPTION = "[1] Previous Page \t [2] Next Page \t [3] Delete Product \t[4] Edit Product \t[5] Add new product \t[0] Exit";
    public static final String ADMIN_DASHBOARD_OPTIONS = "[1] Products Management Dashboard \n[2] Customer Management Dashboard \n[3] Back \n[0] Exit";
    public static final String ADMIN_NO_PRODUCT_ADD_PRODUCT_PROMPT = "There seem to be no products currently, please add new products!";
    public static final String ADMIN_SUCCESSFUL_PRODUCT_CREATION_PROMPT = "Product created successfully!";
    public static final String PAGE_SIZE_EXCEEDED = "Page size exceeded, you have tried to access a page that doesn't exit!";
    public static final String ADMIN_PRODUCT_DESCRIPTION_REQUEST = "What is the new value for description?";
    public static final String ADMIN_PRODUCT_CATEGORY_REQUEST = "Choose a number to for the category";
    public static final String ADMIN_PRODUCT_PRICE_REQUEST = "What is the value for price?";
    public static final String ADMIN_PRODUCT_DASHBOARD_OPTIONS = "[1] View all products \n[2] View Product by Id \n[3] Edit Product by Id \n[4] Delete Product by Id \n[5] Add new Product \n[6] Back \n[0] Exit";
    public static final String ADMIN_CUSTOMER_DASHBOARD_OPTIONS = "[1] View all Customers \n[2] View Customer by Id  \n[3] Back \n[0] Exit";
    public static final String LOGIN_ATTEMPT_EXHAUSTION = "You have exhausted the allocated login attempts!";
    public static final String CUSTOMER_ACCOUNT_CREATION_ATTEMPT_EXHAUSTION = "You have exhausted the allocated account creation attempts!";
    public static final String INPUT_ATTEMPT_EXHAUSTION = "You have exhausted the allocated input attempts!";
    public static final String INVALID_OPTION = "You have entered an invalid option!";
    public static final String EMPTY_STRING_WARNING = "You seem to have entered an empty value.";
    public static final String NUMBER_OUT_OF_RANGE_WARNING = "Your value should be between %d and %d";
    public static final String LOGIN_ATTEMPT_REMAINING_WARNING = "You have %d more login attempt(s).";
    public static final String CUSTOMER_ACCOUNT_CREATION_ATTEMPT_REMAINING_WARNING = "You have %d more account creation attempt(s).";
    public static final String INPUT_ATTEMPT_REMAINING_WARNING = "You have %d more input attempt(s).";
    public static final String LOGIN_INPUT_USERNAME_PROMPT = "Please, input your username";
    public static final String ACCOUNT_CREATION_INPUT_USERNAME_PROMPT = "Please, input your desired username";
    public static final String LOGIN_INPUT_PASSWORD_PROMPT = "Please, input your password";
    public static final String ACCOUNT_CREATION_INPUT_PASSWORD_PROMPT = "Please, input your desired password";
    public static final String ACCOUNT_CREATION_SUCCESSFUL = "Account creation successful, proceed to login with your credentials!";
    public static final String ITEM_ADDED_TO_CART_SUCCESS = "Item added to cart successfully!";
    public static final String ITEM_REMOVED_FROM_CART_SUCCESS = "Item removed from cart successfully!";
    public static final String EXIT_SYSTEM_WARNING = "You have typed in the exit character (0/e/exit), system will now terminate!";
    public static final String NUMERIC_VALUE_REQUIRED_WARNING = "Numeric value required";
    public static final String PRODUCT_META_INFO = "[Current page no: %s] :::: [Total pages: %s] :::: [Total products: %s]";

}
