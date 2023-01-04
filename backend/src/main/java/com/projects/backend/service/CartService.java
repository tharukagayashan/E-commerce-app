package com.projects.backend.service;

import com.projects.backend.config.SecurityConfig;
import com.projects.backend.dto.CartDto;
import com.projects.backend.dto.custom.CalculationDto;
import com.projects.backend.dto.custom.JwtDataDto;
import com.projects.backend.dto.custom.ResponseDto;
import com.projects.backend.model.Cart;
import com.projects.backend.model.Product;
import com.projects.backend.model.User;
import com.projects.backend.repository.CartRepo;
import com.projects.backend.repository.ProductRepo;
import com.projects.backend.repository.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final String MSG_CODE_1 = "TOKEN EMPTY";
    private static final String MSG_CODE_2 = "TOKEN NULL";
    private static final String MSG_CODE_3 = "TOKEN DATA NOT FOUND";
    private static final String MSG_CODE_4 = "PRODUCT NOT FOUND";
    private static final String MSG_CODE_5 = "ITEM ADDED TO CART";
    private static final String MSG_CODE_6 = "ITEM ADDED TO CART FAILED";
    private static final String MSG_CODE_7 = "ITEM LIST FOUND";
    private static final String MSG_CODE_8 = "CART_ID NULL";
    private static final String MSG_CODE_9 = "ITEM UPDATED";
    private static final String MSG_CODE_10 = "CART ITEM NOT FOUND";
    private static final String MSG_CODE_11 = "ITEM DELETED SUCCESSFULLY";
    private static final String MSG_CODE_12 = "USER_ID NULL";
    private static final String MSG_CODE_13 = "USER NOT FOUND";

    private final SecurityConfig securityConfig;
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public CartService(SecurityConfig securityConfig, CartRepo cartRepo, ProductRepo productRepo, UserRepo userRepo) {
        this.securityConfig = securityConfig;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity addItem(String token, CartDto cartDto) {
        ResponseDto response = new ResponseDto();
        if (token.isEmpty()) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        } else if (token == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_2);
        }
        JwtDataDto jwtData = securityConfig.getJWTData(token);
        if (jwtData.getUserId() == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_3);
        }
        Optional<Product> optProduct = productRepo.findById(cartDto.getProductId());
        if (!optProduct.isPresent()) {
            return ResponseEntity.badRequest().body(MSG_CODE_4);
        }
        Cart cart = new Cart();
        cart.setUser(userRepo.findById(jwtData.getUserId()).get());
        cart.setProduct(optProduct.get());
        cart.setCount(cartDto.getCount());
        Cart savedCart = cartRepo.save(cart);
        if (savedCart.getCartId() != null) {
            response.setMessage(MSG_CODE_5);
            response.setData(savedCart);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_6);
        }
    }

    public ResponseEntity getItemsForUser(String token) {
        ResponseDto response = new ResponseDto();
        if (token.isEmpty()) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        } else if (token == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_2);
        }
        JwtDataDto jwtData = securityConfig.getJWTData(token);
        if (jwtData.getUserId() == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_3);
        }

        List<Cart> itemList = cartRepo.findAll().stream().filter(cart -> cart.getUser().getUserId() == jwtData.getUserId()).collect(Collectors.toList());
        response.setMessage(MSG_CODE_7);
        response.setData(itemList);
        return ResponseEntity.ok().body(response);

    }


    public ResponseEntity updateItem(Integer cartId, CartDto cartDto) {
        ResponseDto response = new ResponseDto();
        if (cartId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_8);
        }
        Optional<Cart> optCart = cartRepo.findById(cartId);
        if (optCart.isPresent()) {
            Cart cartData = optCart.get();
            cartData.setCount(cartDto.getCount());
            Cart updatedCart = cartRepo.save(cartData);
            response.setMessage(MSG_CODE_9);
            response.setData(updatedCart);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_10);
        }
    }

    public ResponseEntity deleteItem(Integer cartId) {
        if (cartId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_8);
        }
        Optional<Cart> optCart = cartRepo.findById(cartId);
        if (optCart.isPresent()) {
            cartRepo.deleteById(cartId);
            return ResponseEntity.ok(MSG_CODE_11);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_10);
        }
    }

    public ResponseEntity getCalculations(Integer userId, BigDecimal discount) {
        CalculationDto calculationDto = new CalculationDto();
        BigDecimal total = new BigDecimal(0);
        BigDecimal discountAmount = new BigDecimal(0);
        if (userId == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_12);
        }
        Optional<User> optUser = userRepo.findById(userId);
        if (!optUser.isPresent()) {
            return ResponseEntity.badRequest().body(MSG_CODE_13);
        }
        List<Cart> itemList = cartRepo.findAll().stream().filter(cart -> cart.getUser().getUserId() == userId).collect(Collectors.toList());
        if (itemList.size() == 0) {
            return ResponseEntity.badRequest().body(MSG_CODE_10);
        } else {
            for (Cart c : itemList) {
                total = total.add(new BigDecimal(c.getCount()).multiply(c.getProduct().getPrice()));
            }
        }

        if (!discount.equals(0)) {
            discountAmount = total.divide(new BigDecimal(100)).multiply(discount);
        }
        BigDecimal finalTotal = total.subtract(discountAmount);
        calculationDto.setAmount(total);
        calculationDto.setDiscount(discountAmount);
        calculationDto.setFinalAmount(finalTotal);
        return ResponseEntity.ok().body(calculationDto);
    }
}
