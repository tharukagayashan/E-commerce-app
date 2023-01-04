package com.projects.backend.service;

import com.projects.backend.config.SecurityConfig;
import com.projects.backend.dto.UserDto;
import com.projects.backend.dto.custom.LoginDto;
import com.projects.backend.dto.custom.LoginResponseDto;
import com.projects.backend.dto.custom.ResponseDto;
import com.projects.backend.model.User;
import com.projects.backend.repository.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    private static final String MSG_CODE_1 = "SOME IMPORTANT FIELDS ARE NULL!!!";
    private static final String MSG_CODE_2 = "SOME IMPORTANT FIELDS ARE MISSING!!!";
    private static final String MSG_CODE_3 = "USER REGISTRATION FAILED";
    private static final String MSG_CODE_4 = "USER REGISTRATION SUCCESSFUL";
    private final UserRepo userRepo;
    private final SecurityConfig securityConfig;

    public UserService(UserRepo userRepo, SecurityConfig securityConfig) {
        this.userRepo = userRepo;
        this.securityConfig = securityConfig;
    }

    public ResponseEntity signUp(UserDto userDto) throws NoSuchAlgorithmException {
        ResponseDto response = new ResponseDto();
        if (userDto.getEmail() == null || userDto.getPassword() == null || userDto.getUsername() == null || userDto.getRole() == null) {
            return ResponseEntity.badRequest().body(MSG_CODE_1);
        }
        if (userDto.getEmail().equals("") || userDto.getPassword().equals("") || userDto.getUsername().equals("") || userDto.getRole().equals("")) {
            return ResponseEntity.badRequest().body(MSG_CODE_2);
        }

        String encPassword = securityConfig.hashPassword(userDto.getPassword());

        System.out.println("ENC PASSWORD : " + encPassword);

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());
        user.setPassword(encPassword);
        user.setFullName(user.getFullName());

        User savedUser = userRepo.save(user);
        if (savedUser.getUserId() != null) {
            response.setMessage(MSG_CODE_4);
            response.setData(savedUser);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(MSG_CODE_3);
        }

    }

    public ResponseEntity signIn(LoginDto loginDto) throws NoSuchAlgorithmException {
        ResponseDto response = new ResponseDto();

        if (loginDto.getUsername() == null || loginDto.getPassword() == null) {
            return ResponseEntity.badRequest().body("USERNAME OR PASSWORD NULL");
        } else if (loginDto.getUsername().equals("") || loginDto.getPassword().equals("")) {
            return ResponseEntity.badRequest().body("USERNAME OR PASSWORD EMPTY");
        }

        Optional<User> optUser = userRepo.findUserByUsername(loginDto.getUsername());
        if (optUser.isPresent()) {
            User user = optUser.get();
            Boolean flag = securityConfig.comparePassword(user.getPassword(), loginDto.getPassword());
            if (flag) {

                LoginResponseDto loginResponse = new LoginResponseDto();
                String jwtToken = securityConfig.generateToken(user.getUsername(), user.getUserId());
                loginResponse.setToken(jwtToken);

                response.setMessage("LOGIN SUCCESSFUL");
                response.setData(loginResponse);
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.badRequest().body("PASSWORD INCORRECT");
            }
        } else {
            return ResponseEntity.badRequest().body("USER NOT FOUND");
        }
    }

    public ResponseEntity getProfile(Integer userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body("USER ID NULL");
        }
        Optional<User> optUser = userRepo.findById(userId);
        ResponseDto response = new ResponseDto();
        if (optUser.isPresent()) {
            User user = optUser.get();
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setEmail(user.getEmail());
            userDto.setFullName(user.getFullName());
            userDto.setRole(user.getRole());
            userDto.setPassword(user.getPassword());
            userDto.setUsername(user.getUsername());;

            response.setMessage("USER FOUND");
            response.setData(userDto);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body("USER NOT FOUND");
        }
    }
}