package com.projects.backend.controller;

import com.projects.backend.dto.UserDto;
import com.projects.backend.dto.custom.LoginDto;
import com.projects.backend.dto.custom.ResponseDto;
import com.projects.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto> signUp(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        ResponseEntity response = userService.signUp(userDto);
        return response;
    }

    @PostMapping("/signIn")
    public ResponseEntity<ResponseDto> signIn(@RequestBody LoginDto loginDto) throws NoSuchAlgorithmException {
        ResponseEntity response = userService.signIn(loginDto);
        return response;
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ResponseDto> getProfile(@PathVariable Integer userId){
        ResponseEntity response = userService.getProfile(userId);
        return response;
    }

}
