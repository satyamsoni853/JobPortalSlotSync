package com.SlotSync.SlotSync.Api;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.ResetPasswordDTO;
import com.SlotSync.SlotSync.Dto.ResponseDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import com.SlotSync.SlotSync.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

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

    // Kept your path segment "Login" to avoid unnecessary change; switched to POST
    // to accept a body
    @PostMapping("/Login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException {
        String message = userService.loginUser(loginDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
        // alternatively: return ResponseEntity.ok(loggedIn);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgotPassword(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException {

        return new ResponseEntity<>(new ResponseDTO(userService.changePassword(loginDTO)), HttpStatus.OK);
    }
    

    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDTO> sendOtp(@PathVariable @Email(message = "Invalid email format") String email)
            throws Exception {
        String message = userService.sendOtp(email);
        ResponseDTO response = new ResponseDTO(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable @Email(message = "Invalid email format") String email,
            @Pattern(regexp = "^[0-9]{6}$", message = "Invalid OTP format") @PathVariable String otp) throws Exception {
        String message = userService.verifyOtp(email, otp);
        ResponseDTO response = new ResponseDTO(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) throws JobPortalException {
        String message = userService.resetPassword(resetPasswordDTO);
        ResponseDTO response = new ResponseDTO(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
