package com.iCare.User_Service.dto;

import com.iCare.User_Service.Enums.Roles;
import com.iCare.User_Service.entity.User;

public class SignUpRequestDTO {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;
    private Roles role;

    public SignUpRequestDTO(Long id, String username, String name, String email, String password, Roles role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public SignUpRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public User toEntity(){
        return new User(this.id,this.username,this.name,this.email,this.password,this.role);
    }
}
