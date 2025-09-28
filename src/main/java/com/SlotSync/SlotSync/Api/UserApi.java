package com.SlotSync.SlotSync.Api;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import com.SlotSync.SlotSync.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDto) throws JobPortalException {
        UserDTO newUser = userService.registerUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Kept your path segment "Login" to avoid unnecessary change; switched to POST to accept a body
    @PostMapping("/Login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException {
        UserDTO loggedIn = userService.loginUser(loginDTO);
        return new ResponseEntity<>(loggedIn, HttpStatus.OK);
        // alternatively: return ResponseEntity.ok(loggedIn);
    }
}
