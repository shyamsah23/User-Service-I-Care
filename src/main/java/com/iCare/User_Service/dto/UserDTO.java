package com.iCare.User_Service.dto;

import com.iCare.User_Service.Enums.Roles;
import com.iCare.User_Service.entity.User;
import jakarta.persistence.Column;


public class UserDTO {

    private Long id;
    private String username;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private Roles role;
    private Long profileId;

    public UserDTO(Long id, String username, String name, String email, String password, Roles role, Long profileId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profileId = profileId;
    }

    public UserDTO() {
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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public User toEntity() {
        return new User(this.id, this.username, this.name, this.email, this.password, this.role, this.profileId);
    }
}
