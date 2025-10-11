package com.iCare.User_Service.service;

import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.entity.User;

public interface UserService {
    public void registerUser(UserDTO user);
    public UserDTO loginUser(UserDTO user);
    public UserDTO getUserById(Long id);
}
