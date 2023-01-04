package com.projects.backend.controller;

import com.projects.backend.dto.ProductDto;
import com.projects.backend.dto.custom.ResponseDto;
import com.projects.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createProduct(@RequestBody ProductDto product) {
        ResponseEntity response = productService.createProduct(product);
        return response;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getProductList() {
        ResponseEntity response = productService.getProductList();
        return response;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto> getProductById(@PathVariable Integer productId) {
        ResponseEntity response = productService.getProductById(productId);
        return response;
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateProduct(@RequestBody ProductDto product) {
        ResponseEntity response = productService.updateProduct(product);
        return response;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        ResponseEntity response = productService.deleteProduct(productId);
        return response;
    }

}
