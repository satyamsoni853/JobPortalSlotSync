package com.SlotSync.SlotSync.Service;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.ResetPasswordDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;

public interface UserService {
    String registerUser(UserDTO userDto) throws JobPortalException;
    UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException;
    String sendOtp(String email) throws Exception; // <-- add this
    String verifyOtp(String email, String otp) throws Exception; // <-- add this
    String changePassword(LoginDTO loginDTO) throws JobPortalException;
    String resetPassword(ResetPasswordDTO resetPasswordDTO) throws JobPortalException;
}
