package com.iCare.User_Service.service;

import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(UserDTO user) {

    }

    @Override
    public UserDTO loginUser(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }
}
