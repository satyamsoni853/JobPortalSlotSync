package com.SlotSync.SlotSync.Service;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;

public interface UserService {
    String registerUser(UserDTO userDto) throws JobPortalException;
    String loginUser(LoginDTO loginDTO) throws JobPortalException; // <-- add this
    String sendOtp(String email) throws Exception; // <-- add this
    String verifyOtp(String email, String otp) throws Exception; // <-- add this
    String changePassword(LoginDTO loginDTO) throws JobPortalException;
}
