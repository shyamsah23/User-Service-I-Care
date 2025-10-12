package com.iCare.User_Service.dto;

public class LoginResponseDTO {

    private String username;
    private String jwtToken;

    public LoginResponseDTO(String username, String jwtToken) {
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public LoginResponseDTO() {
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
