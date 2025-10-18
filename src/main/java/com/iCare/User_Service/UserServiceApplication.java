package com.iCare.User_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        System.out.println("This is user service");
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
