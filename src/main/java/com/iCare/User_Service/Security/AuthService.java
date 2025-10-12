package com.iCare.User_Service.Security;

import com.iCare.User_Service.dto.LoginRequestDTO;
import com.iCare.User_Service.dto.LoginResponseDTO;
import com.iCare.User_Service.dto.SignUpRequestDTO;
import com.iCare.User_Service.dto.SignUpResponseDTO;
import com.iCare.User_Service.entity.User;
import com.iCare.User_Service.exception.UserException;
import com.iCare.User_Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) throws UserException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getusername(),loginRequestDTO.getPassword())
        );

        if(auth == null){
            throw new UserException("Invalid Credentials");
        }
        User user = (User) auth.getPrincipal();
        String token = authUtil.generateToken(user);
        return new LoginResponseDTO(user.getUsername(),token);
    }

    public SignUpResponseDTO signUpUser(SignUpRequestDTO signUpRequestDTO) throws UserException {
        Optional<User> user = userRepository.findByUsername(signUpRequestDTO.getUsername());
        if (user.isPresent()) {
            throw new UserException("User Already Exist with Given Username");
        }
        // Hash the password
        signUpRequestDTO.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        // Save in the database
        User savedUser = userRepository.save(new User(signUpRequestDTO.getId(), signUpRequestDTO.getUsername(), signUpRequestDTO.getName(), signUpRequestDTO.getEmail()
                , passwordEncoder.encode(signUpRequestDTO.getPassword()), signUpRequestDTO.getRole()));

        return new SignUpResponseDTO("User SignUp Successfully",savedUser.getUsername());
    }

}
