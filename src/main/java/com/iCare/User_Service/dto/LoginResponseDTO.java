package com.iCare.User_Service.dto;

public class LoginResponseDTO {

    private String username;
    private String jwtToken;
    private Long id;

    public LoginResponseDTO(String username, String jwtToken,Long id) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.id=id;
    }

    public LoginResponseDTO() {
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
