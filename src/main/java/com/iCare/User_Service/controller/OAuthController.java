package com.iCare.User_Service.controller;

import com.iCare.User_Service.Enums.Roles;
import com.iCare.User_Service.Security.AuthUtil;
import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.repository.UserRepository;
import com.iCare.User_Service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth/google")
public class OAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtil authUtil;

    Logger logger = LoggerFactory.getLogger(OAuthController.class);

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            logger.info("sending authentication code to get token");
            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            logger.info("params details={}", params);
            logger.info("headers details={}", headers);
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                    tokenEndpoint,
                    HttpMethod.POST,
                    request,
                    Map.class
            );
            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                logger.info("checks performed on user availability");
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");
                UserDTO userDetails = userService.getUserByEmail(email);
                if (userDetails == null) {
                    logger.info("user data not found");
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(email);
                    userDTO.setUsername(email);
                    userDTO.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    userDTO.setRole(Roles.PATIENT);
                    userRepository.save(userDTO.toEntity());
                }
                String jwtToken = authUtil.generateToken(userService.getUserByEmail(email).toEntity());
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            logger.info("Exception occurred with error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
