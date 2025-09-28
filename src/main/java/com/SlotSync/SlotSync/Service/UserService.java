package com.SlotSync.SlotSync.Service;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;

public interface UserService {
    UserDTO registerUser(UserDTO userDto) throws JobPortalException;
    UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException; // <-- add this
}
