package com.SlotSync.SlotSync.Api;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.ResponseDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import com.SlotSync.SlotSync.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDTO userDto) throws JobPortalException {
        String message = userService.registerUser(userDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Kept your path segment "Login" to avoid unnecessary change; switched to POST to accept a body
    @PostMapping("/Login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException {
        String message = userService.loginUser(loginDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
        // alternatively: return ResponseEntity.ok(loggedIn);
    }
    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDTO> sendOtp(@PathVariable String email) throws Exception {
        String message = userService.sendOtp(email);
        ResponseDTO response = new ResponseDTO(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
