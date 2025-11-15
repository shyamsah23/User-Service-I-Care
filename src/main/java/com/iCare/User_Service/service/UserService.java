package com.iCare.User_Service.service;

import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;

public interface UserService {
    public void registerUser(UserDTO user) throws UserException;

    public UserDTO loginUser(UserDTO user) throws UserException;

    public UserDTO getUserById(Long id) throws UserException;

    public UserDTO getUserByEmail(String email);
}
