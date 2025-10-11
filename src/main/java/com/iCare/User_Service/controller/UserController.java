package com.iCare.User_Service.controller;

import com.iCare.User_Service.dto.ResponseDTO;
import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.service.UserService;
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

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid  UserDTO userDTO) throws UserException {
        userService.registerUser(userDTO);
        return new ResponseEntity<>(new ResponseDTO("Account Created"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) throws UserException {
        UserDTO user = userService.loginUser(userDTO);
        return ResponseEntity.ok(user);
    }

}
