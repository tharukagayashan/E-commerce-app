package com.projects.backend.service;

import com.projects.backend.dto.CategoryDto;
import com.projects.backend.dto.custom.ResponseDto;
import com.projects.backend.model.Category;
import com.projects.backend.model.Product;
import com.projects.backend.repository.CategoryRepo;
import com.projects.backend.repository.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private static final String MSG_CODE_1 = "SOME IMPORTANT FIELDS ARE NULL!!!";
    private static final String MSG_CODE_2 = "SOME IMPORTANT FIELDS ARE MISSING!!!";
    private static final String MSG_CODE_3 = "CATEGORY NAME ALREADY USED";
    private static final String MSG_CODE_4 = "CATEGORY CREATED SUCCESSFULLY";
    private static final String MSG_CODE_5 = "CATEGORY CREATION FAILED";
    private static final String MSG_CODE_6 = "CATEGORY NOT FOUND";
    private static final String MSG_CODE_7 = "CATEGORY FOUND";
    private static final String MSG_CODE_8 = "CATEGORY UPDATED";
    private static final String MSG_CODE_9 = "CATEGORY DELETE FAILED";
    private static final String MSG_CODE_10 = "CATEGORY DELETED";
    private static final String MSG_CODE_11 = "PRODUCT NOT FOUND";
    private static final String MSG_CODE_12 = "PRODUCT FOUND";
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    public CategoryService(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    public ResponseEntity createCategory(CategoryDto category) {
        ResponseDto response = new ResponseDto();
        if (category.getCategoryName() == null || category.getImageUrl() == null || category.getDescription() == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        if (category.getCategoryName().equals("") || category.getDescription().equals("") || category.getImageUrl().equals("")) {
            return ResponseEntity.badRequest().body(MSG_CODE_2);
        }
        Optional<Category> optCategory = categoryRepo.findByCategoryName(category.getCategoryName());
        if (optCategory.isPresent()) {
            return ResponseEntity.badRequest().body(MSG_CODE_3);
        }

        Category data = new Category();
        data.setCategoryName(category.getCategoryName());
        data.setDescription(category.getDescription());
        data.setImageUrl(category.getImageUrl());

        Category savedCategory = categoryRepo.save(data);
        if (savedCategory.getCategoryId() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(savedCategory.getCategoryId());
            categoryDto.setCategoryName(savedCategory.getCategoryName());
            categoryDto.setDescription(savedCategory.getDescription());
            categoryDto.setImageUrl(savedCategory.getImageUrl());
            response.setMessage(MSG_CODE_4);
            response.setData(categoryDto);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_5);
        }
    }

    public ResponseEntity getCategoryList() {
        ResponseDto response = new ResponseDto();
        List<Category> categoryList = categoryRepo.findAll();
        if (categoryList.size() == 0) {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        } else {
            response.setMessage(MSG_CODE_7);
            response.setData(categoryList);
            return ResponseEntity.ok().body(response);
        }
    }

    public ResponseEntity getCategoryById(Integer categoryId) {
        ResponseDto response = new ResponseDto();
        if (categoryId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        Optional<Category> optCategory = categoryRepo.findById(categoryId);
        if (optCategory.isPresent()) {

            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(optCategory.get().getCategoryId());
            categoryDto.setCategoryName(optCategory.get().getCategoryName());
            categoryDto.setDescription(optCategory.get().getDescription());
            categoryDto.setImageUrl(optCategory.get().getImageUrl());

            response.setMessage(MSG_CODE_7);
            response.setData(categoryDto);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
    }

    public ResponseEntity updateCategory(CategoryDto category) {
        ResponseDto response = new ResponseDto();
        if (category.getCategoryId() == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        Optional<Category> optCategory = categoryRepo.findById(category.getCategoryId());
        if (optCategory.isPresent()) {
            Category data = new Category();
            data.setCategoryId(category.getCategoryId());
            data.setCategoryName(category.getCategoryName());
            data.setDescription(category.getDescription());
            data.setImageUrl(category.getImageUrl());
            Category updatedCategory = categoryRepo.save(data);

            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(updatedCategory.getCategoryId());
            categoryDto.setCategoryName(updatedCategory.getCategoryName());
            categoryDto.setDescription(updatedCategory.getDescription());
            categoryDto.setImageUrl(updatedCategory.getImageUrl());

            response.setMessage(MSG_CODE_8);
            response.setData(categoryDto);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
    }

    public ResponseEntity deleteCategory(Integer categoryId) {
        if (categoryId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        Optional<Category> optCategory = categoryRepo.findById(categoryId);
        if (optCategory.isPresent()) {
            categoryRepo.deleteById(categoryId);
            Optional<Category> checkCategory = categoryRepo.findById(categoryId);
            if (checkCategory.isPresent()) {
                return ResponseEntity.badRequest().body(MSG_CODE_9);
            } else {
                return ResponseEntity.ok().body(MSG_CODE_10);
            }
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
    }

    public ResponseEntity getProductsForCategory(Integer categoryId) {
        ResponseDto response = new ResponseDto();
        if (categoryId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        Optional<Category> optCategory = categoryRepo.findById(categoryId);
        if (!optCategory.isPresent()) {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
        List<Product> productList = productRepo.findAll().stream().filter(
                product -> product.getCategory().getCategoryId() == categoryId).collect(Collectors.toList());
        if (productList.size() == 0) {
            return ResponseEntity.badRequest().body(MSG_CODE_11);
        }
        response.setMessage(MSG_CODE_12);
        response.setData(productList);
        return ResponseEntity.ok().body(response);
    }
}