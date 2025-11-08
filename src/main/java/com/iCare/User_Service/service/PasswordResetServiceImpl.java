package com.iCare.User_Service.service;

import com.iCare.User_Service.entity.PasswordReset;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.repository.PasswordResetTokenRepository;
import com.iCare.User_Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendResetLink(String email) throws UserException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserException("User Does not Exist with Mail id");
        }

        User user = userOptional.get();
        String token = UUID.randomUUID().toString();

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setToken(token);
        passwordReset.setUser(user);
        passwordReset.setExpireAt(LocalDateTime.now().plusMinutes(15));
    }
}
