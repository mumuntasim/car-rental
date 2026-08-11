package com.softuni.car_rental.service;
import com.softuni.car_rental.model.dto.user.UserRegisterDTO;
import com.softuni.car_rental.model.entity.user.User;
import com.softuni.car_rental.model.entity.user.UserRole;
import com.softuni.car_rental.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());

        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        user.setRole(UserRole.USER);

        userRepository.save(user);
    }
}