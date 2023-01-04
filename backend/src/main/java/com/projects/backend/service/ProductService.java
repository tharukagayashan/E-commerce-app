package com.projects.backend.service;

import com.projects.backend.dto.ProductDto;
import com.projects.backend.dto.custom.ResponseDto;
import com.projects.backend.model.Category;
import com.projects.backend.model.Product;
import com.projects.backend.repository.CategoryRepo;
import com.projects.backend.repository.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final String MSG_CODE_1 = "SOME IMPORTANT FIELDS ARE NULL!!!";
    private static final String MSG_CODE_2 = "SOME IMPORTANT FIELDS ARE MISSING!!!";
    private static final String MSG_CODE_3 = "PRODUCT NAME ALREADY USED";
    private static final String MSG_CODE_4 = "PRODUCT CREATED SUCCESSFULLY";
    private static final String MSG_CODE_5 = "PRODUCT CREATION FAILED";
    private static final String MSG_CODE_6 = "PRODUCT NOT FOUND";
    private static final String MSG_CODE_7 = "PRODUCT FOUND";
    private static final String MSG_CODE_8 = "PRODUCT UPDATED";
    private static final String MSG_CODE_9 = "PRODUCT DELETE FAILED";
    private static final String MSG_CODE_10 = "PRODUCT DELETED";
    private static final String MSG_CODE_11 = "CATEGORY NOT FOUND";

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public ResponseEntity createProduct(ProductDto product) {
        ResponseDto response = new ResponseDto();
        if (product.getProductName() == null || product.getImageUrl() == null || product.getDescription() == null || product.getPrice() == null || product.getCategoryId() == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        if (product.getProductName().equals("") || product.getDescription().equals("") || product.getImageUrl().equals("")) {
            return ResponseEntity.badRequest().body(MSG_CODE_2);
        }

        Optional<Category> optionalCategory = categoryRepo.findById(product.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.badRequest().body(MSG_CODE_11);
        }

        Optional<Product> optProduct = productRepo.findByProductName(product.getProductName());
        if (optProduct.isPresent()) {
            return ResponseEntity.badRequest().body(MSG_CODE_3);
        }
        Product data = new Product();
        data.setProductName(product.getProductName());
        data.setDescription(product.getDescription());
        data.setImageUrl(product.getImageUrl());
        data.setPrice(product.getPrice());
        data.setCategory(categoryRepo.findById(product.getCategoryId()).get());

        Product savedProduct = productRepo.save(data);
        if (savedProduct.getProductId() != null) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(savedProduct.getProductId());
            productDto.setProductName(savedProduct.getProductName());
            productDto.setDescription(savedProduct.getDescription());
            productDto.setImageUrl(savedProduct.getImageUrl());
            productDto.setPrice(savedProduct.getPrice());
            productDto.setCategoryId(savedProduct.getCategory().getCategoryId());
            response.setMessage(MSG_CODE_4);
            response.setData(productDto);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_5);
        }
    }

    public ResponseEntity getProductList() {
        ResponseDto response = new ResponseDto();
        List<Product> productList = productRepo.findAll();
        if (productList.size() == 0) {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        } else {
            response.setMessage(MSG_CODE_7);
            response.setData(productList);
            return ResponseEntity.ok().body(response);
        }
    }

    public ResponseEntity getProductById(Integer productId) {
        ResponseDto response = new ResponseDto();
        if (productId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        Optional<Product> optProduct = productRepo.findById(productId);
        if (optProduct.isPresent()) {

            ProductDto productDto = new ProductDto();
            productDto.setProductId(optProduct.get().getProductId());
            productDto.setProductName(optProduct.get().getProductName());
            productDto.setDescription(optProduct.get().getDescription());
            productDto.setImageUrl(optProduct.get().getImageUrl());
            productDto.setPrice(optProduct.get().getPrice());
            productDto.setCategoryId(optProduct.get().getCategory().getCategoryId());

            response.setMessage(MSG_CODE_7);
            response.setData(productDto);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
    }

    public ResponseEntity updateProduct(ProductDto product) {
        ResponseDto response = new ResponseDto();
        if (product.getProductId() == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        Optional<Product> optProduct = productRepo.findById(product.getProductId());
        if (optProduct.isPresent()) {
            Product data = new Product();
            data.setProductId(product.getProductId());
            data.setProductName(product.getProductName());
            data.setDescription(product.getDescription());
            data.setImageUrl(product.getImageUrl());
            data.setPrice(product.getPrice());
            data.setCategory(categoryRepo.findById(product.getCategoryId()).get());
            Product updatedProduct = productRepo.save(data);

            ProductDto productDto = new ProductDto();
            productDto.setProductId(updatedProduct.getProductId());
            productDto.setProductName(updatedProduct.getProductName());
            productDto.setDescription(updatedProduct.getDescription());
            productDto.setImageUrl(updatedProduct.getImageUrl());
            productDto.setPrice(updatedProduct.getPrice());
            productDto.setCategoryId(updatedProduct.getCategory().getCategoryId());

            response.setMessage(MSG_CODE_8);
            response.setData(productDto);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
    }

    public ResponseEntity deleteProduct(Integer productId) {
        if (productId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        Optional<Product> optProduct = productRepo.findById(productId);
        if (optProduct.isPresent()) {
            productRepo.deleteById(productId);
            Optional<Product> checkProduct = productRepo.findById(productId);
            if (checkProduct.isPresent()) {
                return ResponseEntity.badRequest().body(MSG_CODE_9);
            } else {
                return ResponseEntity.ok().body(MSG_CODE_10);
            }
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
    }
}
