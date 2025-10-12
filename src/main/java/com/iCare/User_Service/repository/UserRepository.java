package com.iCare.User_Service.repository;

import com.iCare.User_Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);
}
