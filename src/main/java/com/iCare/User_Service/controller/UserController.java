package com.iCare.User_Service.controller;

import com.iCare.User_Service.Security.AuthService;
import com.iCare.User_Service.dto.*;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.service.UserService;
import com.iCare.User_Service.service.UserServiceImpl;
import jakarta.validation.Valid;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserServiceImpl userServiceImpl;

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

}
