//package com.damzxyno.foodstore.controller.old;
//
//import com.damzxyno.foodstore.dto.request.product.ProductCreationRequest;
//import com.damzxyno.foodstore.dto.request.product.ProductModificationRequest;
//import com.damzxyno.foodstore.dto.response.product.PaginatedProductsResponse;
//import com.damzxyno.foodstore.dto.response.product.ProductDetailsResponse;
//import com.damzxyno.foodstore.enums.ProductCategory;
//import com.damzxyno.foodstore.service.interfaces.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("product")
//public class ProductController {
//    private final ProductService prodService;
//
//    @GetMapping
//    public ResponseEntity<PaginatedProductsResponse> getAllProducts(
//            @RequestParam(name = "search", required = false) String search,
//            @RequestParam(name = "category", required = false) List<ProductCategory> category,
//            @RequestParam(name = "pageNo", required = false) int pageNo,
//            @RequestParam(name = "pageSize", required = false) int pageSize
//    ){
//        var res = prodService.getAll(search, category, pageNo, pageSize);
//        return ResponseEntity.ok(res);
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<ProductDetailsResponse> getProductById(@PathVariable(name = "id") Long id){
//        var resp = prodService.getProductById(id);
//        return resp.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping
//    public ResponseEntity<Long> addNewProduct(@RequestBody ProductCreationRequest request){
//        var res = prodService.createProduct(request);
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Long> modifyProductById(@PathVariable(name = "id") Long id, @RequestBody ProductModificationRequest request){
//        request.setId(id);
//        var res = prodService.modifyProduct(request);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Long> deleteProductById(){
//        return null;
//    }
//}
