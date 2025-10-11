package com.iCare.User_Service.dto;

import com.iCare.User_Service.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserDTO {

    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;

    public User toEntity(){
        return new User(this.id,this.name,this.email,this.password,this.role);
    }
}
