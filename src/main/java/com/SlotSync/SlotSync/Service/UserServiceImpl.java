package com.SlotSync.SlotSync.Service;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Entity.User;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import com.SlotSync.SlotSync.Repository.UserRepository;
import com.SlotSync.SlotSync.Utilities.Utilities;

import jakarta.mail.internet.MimeMessage;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String registerUser(UserDTO userDto) throws JobPortalException {
        System.out.println("Registering user DTO: " + userDto);

        String email = userDto.getEmail() == null ? null : userDto.getEmail().trim().toLowerCase();
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            throw new JobPortalException("User already exists");
        }

        userDto.setId(String.valueOf(Utilities.getNextSequence("users")));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // keep normalized email in entity
        User user = new User(userDto.getId(), userDto.getName(), email, userDto.getPassword(), userDto.getAccountType());

        userRepository.save(user);

        return "Register successfully";
    }

    @Override
    public String loginUser(LoginDTO loginDTO) throws JobPortalException {
        String email = loginDTO.getEmail() == null ? null : loginDTO.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new JobPortalException("Invalid email or password");
        }

        return "Login successfully";
    }
    @Override
    public String sendOtp(String email) throws Exception {
        email = email == null ? null : email.trim().toLowerCase();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject("Your OTP Code");
        String otp = Utilities.generateOtp();
        helper.setText("Your OTP is: " + otp);
        javaMailSender.send(mimeMessage);
        return "OTP sent successfully";
    }
}
