package com.SlotSync.SlotSync.Service;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.ResetPasswordDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Entity.OTP;
import com.SlotSync.SlotSync.Entity.Profile;
import com.SlotSync.SlotSync.Entity.User;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import com.SlotSync.SlotSync.Repository.UserRepository;
import com.SlotSync.SlotSync.UtilitiesFile.Data;
import com.SlotSync.SlotSync.UtilitiesFile.Utilities;


import jakarta.mail.internet.MimeMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import com.SlotSync.SlotSync.Repository.OTPRepositary;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private OTPRepositary otpRepositary;

    private final Utilities utilities;
    UserServiceImpl(Utilities utilities) {
        this.utilities = utilities;
    }

    @Autowired
    private ProfileService profileService;


    @Override
    public UserDTO registerUser(UserDTO userDto) throws JobPortalException {
       Optional <User> optional =userRepository.findByEmail(userDto.getEmail().trim().toLowerCase());
       if(optional.isPresent()){
        throw new JobPortalException("USER_ALREADY_EXISTS");
       }
       userDto.setProfileId(ProfileService.createProfile(userDto.getEmail());
       userDto.setId(String.valueOf(utilities.getNextSequence("user")));
       userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
       User user =userDto.toEntity();
       user=userRepository.save(user);
       return user.toDTO();
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
        userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        String generatedOtp = Utilities.generateOtp();
        Optional<OTP> otpOptional = otpRepositary.findByEmail(email);

        OTP otp;
        if (otpOptional.isPresent()) {
            otp = otpOptional.get();
            otp.setOtp(generatedOtp);
            otp.setCreationTime(LocalDateTime.now());
        } else {
            otp = new OTP(email, generatedOtp, LocalDateTime.now());
        }
        otpRepositary.save(otp);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(String.format(Data.getMessageBody(email), generatedOtp), true);
        helper.setTo(email);
        helper.setSubject("Your OTP Code");

        javaMailSender.send(mimeMessage);
        return "OTP sent successfully";
    }
    @Override
    public String verifyOtp(String email,String otp) throws Exception {
        OTP otpEntity = otpRepositary.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("OTP_NOT_FOUND"));

           if (!otpEntity.getOtp().equals(otp)) {
            throw new JobPortalException("INVALID_OTP");
           }

           return "OTP verified successfully";
       }
    @Override
    public String changePassword(LoginDTO loginDTO) throws JobPortalException {
        String email = loginDTO.getEmail() == null ? null : loginDTO.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        userRepository.save(user);

        return "Password changed successfully";
    }

    @Scheduled(fixedRate = 300000) // Runs every 5 minutes
     public void RemoveExpiredOtps() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(5);
        List<OTP> expiredOtps = otpRepositary.findAllByCreationTimeBefore(expirationTime);
        if (!expiredOtps.isEmpty()) {
            otpRepositary.deleteAll(expiredOtps);
            System.out.println("Deleted " + expiredOtps.size() + " expired OTP(s).");
        }
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) throws JobPortalException {
        try {
            verifyOtp(resetPasswordDTO.getEmail(), resetPasswordDTO.getOtp());
        } catch (Exception e) {
            throw new JobPortalException(e.getMessage());
        }

        String email = resetPasswordDTO.getEmail().trim().toLowerCase();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);

        return "Password reset successfully";
    }
   }