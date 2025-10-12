package com.iCare.User_Service.dto;

import com.iCare.User_Service.entity.User;

public class SignUpResponseDTO {

    private String message;
    private String username;

    public SignUpResponseDTO(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public SignUpResponseDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SignUpResponseDTO{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
