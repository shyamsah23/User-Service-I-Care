package com.iCare.User_Service.service;

import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserDTO userDTO) throws UserException {
        Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
        if (optional.isPresent()) {
            throw new UserException("User Alraedy Present");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userDTO.toEntity());

    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) throws UserException {
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new UserException("User Not Found"));

        if(!passwordEncoder.matches(userDTO.getPassword(),user.getPassword())) {
            throw new UserException("Password not Matched");
        }
        user.setPassword(null);
        return user.toDTO();
    }

    @Override
    public UserDTO getUserById(Long id) throws UserException {
        User user =userRepository.findById(id).orElseThrow(() -> new UserException("User Not found With the given Id"));
        return user.toDTO();
    }
}
