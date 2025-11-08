package com.iCare.User_Service.service;

import com.iCare.User_Service.exception.UserException;

public interface PasswordResetService {

    public void sendResetLink(String email) throws UserException;

    public Long resetPassword(String token, String newPassword) throws UserException;
}
