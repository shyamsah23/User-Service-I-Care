package com.iCare.User_Service.dto;

import com.iCare.User_Service.entity.User;
import jakarta.persistence.Column;



public class UserDTO {

    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;

    public UserDTO(Long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User toEntity(){
        return new User(this.id,this.name,this.email,this.password,this.role);
    }
}
