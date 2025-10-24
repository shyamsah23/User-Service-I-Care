package com.iCare.User_Service.service;

import com.iCare.User_Service.Enums.Roles;
import com.iCare.User_Service.client.ProfileServiceFeignClient;
import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// This is extra implemenation -> we are not using it..We are using AuthService

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileServiceFeignClient profileServiceFeignClient;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void registerUser(UserDTO userDTO) throws UserException {
        Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
        if (optional.isPresent()) {
            throw new UserException("User Already Present");
        }

        logger.info("User created for user name = {}", userDTO.getName());
        if (userDTO.getRole().equals(Roles.DOCTOR)) {
            logger.info("Profile service being invoked for doctor name={}",userDTO.getName());
            Long profileId=profileServiceFeignClient.addDoctor(userDTO);
            userDTO.setProfileId(profileId);
        } else if (userDTO.getRole().equals(Roles.PATIENT)) {
            logger.info("Profile service being invoked for patient name={}",userDTO.getName());
            Long profileId=profileServiceFeignClient.addPatient(userDTO);
            userDTO.setProfileId(profileId);
        }
        logger.info("Profile created Successfully for name ={} with role={}", userDTO.getName(), userDTO.getRole());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userDTO.toEntity());
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) throws UserException {
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new UserException("User Not Found"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new UserException("Password not Matched");
        }
        user.setPassword(null);
        return user.toDTO();
    }

    @Override
    public UserDTO getUserById(Long id) throws UserException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User Not found With the given Id"));
        return user.toDTO();
    }
}
