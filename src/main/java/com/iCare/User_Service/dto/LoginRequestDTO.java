package com.iCare.User_Service.dto;


public class LoginRequestDTO {

    private String username;
    private String password;

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequestDTO() {
    }
}
