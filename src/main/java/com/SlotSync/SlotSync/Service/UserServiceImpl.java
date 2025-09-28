package com.SlotSync.SlotSync.Service;

import com.SlotSync.SlotSync.Dto.LoginDTO;
import com.SlotSync.SlotSync.Dto.UserDTO;
import com.SlotSync.SlotSync.Entity.User;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import com.SlotSync.SlotSync.Repositary.UserRepository;
import com.SlotSync.SlotSync.Utility.Utilities;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDto) throws JobPortalException {
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

        User saved = userRepository.save(user);
        UserDTO savedDto = saved.toDTO();
        savedDto.setPassword(null); // do not expose password

        System.out.println("Saved user DTO: " + savedDto);
        return savedDto;
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {
        String email = loginDTO.getEmail() == null ? null : loginDTO.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new JobPortalException("Invalid email or password");
        }

        UserDTO dto = user.toDTO();
        dto.setPassword(null); // never return password
        return dto;
    }
}
