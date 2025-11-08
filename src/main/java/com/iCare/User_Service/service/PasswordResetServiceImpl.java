package com.iCare.User_Service.service;

import com.iCare.User_Service.client.NotificationServiceFeignClient;
import com.iCare.User_Service.dto.EmailDTO;
import com.iCare.User_Service.entity.PasswordReset;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.repository.PasswordResetTokenRepository;
import com.iCare.User_Service.repository.UserRepository;
import com.iCare.User_Service.utility.NotificationConstant;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    Logger log = LoggerFactory.getLogger(PasswordResetServiceImpl.class);

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationServiceFeignClient notificationServiceFeignClient;

    private String loadTemplateWithNameAndToken(String restUrl, String name) {
        try {
            String template = Files.readString(Paths.get("src/main/resources/templates/forgot-password-template.html"));
            template = template.replace("{{resetLink}}", restUrl);
            template = template.replace("{{name}}", name);
            return template;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void sendResetLink(String email) throws UserException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {

            log.info("User not found");
            throw new UserException("User Does not Exist with Mail id");
        }

        log.info("User found -> executing other operations");

        User user = userOptional.get();
        String token = UUID.randomUUID().toString();

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setToken(token);
        passwordReset.setUser(user);
        passwordReset.setExpireAt(LocalDateTime.now().plusMinutes(15));
        passwordResetTokenRepository.save(passwordReset);
        log.info("password reset token generated successfully");

        String restUrl = "http://localhost:8082/auth/user/reset-passowrd?token=" + token;

        String emailBody = loadTemplateWithNameAndToken(restUrl, user.getName());
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setRelatedEntityId(user.getId());
        emailDTO.setTo(email);
        emailDTO.setCc(email);
        emailDTO.setBody(emailBody);
        emailDTO.setSubject(NotificationConstant.PASSWORD_RESET_SUBJECT);
        try{
            notificationServiceFeignClient.sendMail(emailDTO);
        } catch (Exception e) {
            log.info("Exception while sending mail with message = {}",e.getMessage());
        }

        log.info("Mail Sent Successfully");

    }

    @Transactional
    @Override
    public Long resetPassword(String token, String newPassword) throws UserException {
        log.info("Request received in Service to reset the password");
        PasswordReset passwordReset = passwordResetTokenRepository.findByToken(token).
                orElseThrow(() -> new UserException("Token is Incorrect"));
        log.info("User found");

        if (passwordReset.getExpireAt().isBefore(LocalDateTime.now())) {
            log.info("Reset link expired");
            throw new UserException("Reset link is expired");
        }

        User user = passwordReset.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordReset);
        return user.getId();
    }
}
