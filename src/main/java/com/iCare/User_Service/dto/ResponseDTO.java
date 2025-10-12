package com.iCare.User_Service.dto;


public class ResponseDTO {
    private String username;

    public ResponseDTO() {
    }

    public ResponseDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
