package com.iCare.User_Service.dto;

import com.iCare.User_Service.entity.PasswordReset;
import com.iCare.User_Service.entity.User;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

public class PasswordResetDTO {

    private Long id;

    private String token;
    private LocalDateTime expireAt;
    private User user;

    public PasswordResetDTO(Long id, String token, LocalDateTime expireAt, User user) {
        this.id = id;
        this.token = token;
        this.expireAt = expireAt;
        this.user = user;
    }

    public PasswordResetDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public PasswordReset toEntity() {
        return new PasswordReset(this.id,this.token,this.expireAt,this.user);
    }
}
