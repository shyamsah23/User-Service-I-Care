package com.iCare.User_Service.controller;

import com.iCare.User_Service.Security.AuthService;
import com.iCare.User_Service.dto.*;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.service.PasswordResetServiceImpl;
import com.iCare.User_Service.service.UserService;
import com.iCare.User_Service.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
@Validated
@CrossOrigin
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PasswordResetServiceImpl passwordResetService;

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid UserDTO userDTO) throws UserException {
//        SignUpResponseDTO response = authService.signUpUser(signUpRequestDTO);
        userServiceImpl.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws UserException {

        LoginResponseDTO user = authService.loginUser(loginRequestDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testToken() {
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) throws UserException {
        logger.info("Request received in controller for forgot password for email = {}", forgotPasswordRequestDTO.getEmail());
        passwordResetService.sendResetLink(forgotPasswordRequestDTO.getEmail());
        logger.info("Mail Sent Successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Long> resetPassword(@RequestParam String token, @RequestBody String password) throws UserException {
        logger.info("Request received in controller to reset the password");
        Long id = passwordResetService.resetPassword(token, password);
        logger.info("Password Reset Done");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
