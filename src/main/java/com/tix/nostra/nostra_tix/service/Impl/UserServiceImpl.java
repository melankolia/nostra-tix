package com.tix.nostra.nostra_tix.service.Impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.User;
import com.tix.nostra.nostra_tix.dto.UserDetailResponseDTO;
import com.tix.nostra.nostra_tix.dto.UserLoginDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterRequestDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterResponseDTO;
import com.tix.nostra.nostra_tix.exception.DuplicateUserDataException;
import com.tix.nostra.nostra_tix.exception.ResourceNotFoundException;
import com.tix.nostra.nostra_tix.projection.UserDetailProjection;
import com.tix.nostra.nostra_tix.repository.UserRepository;
import com.tix.nostra.nostra_tix.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserLoginDTO login(UserLoginDTO userLoginDTO) {
        try {
            User user = userRepository.findByEmailOrPhoneNo(userLoginDTO.userLogin(), userLoginDTO.userLogin());
            if (user == null) {
                throw new RuntimeException("User not found with login: " + userLoginDTO.userLogin());
            }
            if (!user.getPassword().equals(userLoginDTO.password())) {
                throw new RuntimeException("Invalid password for user: " + userLoginDTO.userLogin());
            }
            return new UserLoginDTO(user.getEmail(), user.getPassword(), user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error during login process", e);
        }
    }

    @Override
    public UserRegisterResponseDTO register(UserRegisterRequestDTO user) {
        User existingUser = userRepository.findByEmailOrPhoneNo(user.email(), user.phoneNo());
        if (existingUser != null) {
            if (existingUser.getEmail().equals(user.email())) {
                throw new DuplicateUserDataException("Email already registered: " + user.email());
            }
            if (existingUser.getPhoneNo().equals(user.phoneNo())) {
                throw new DuplicateUserDataException("Phone number already registered: " + user.phoneNo());
            }
        }

        User userToRegister = new User();
        userToRegister.setName(user.name());
        userToRegister.setEmail(user.email());
        userToRegister.setPhoneNo(user.phoneNo());
        userToRegister.setPassword(user.password());
        userToRegister.setRole("ROLE_" + user.role());

        userRepository.save(userToRegister);
        return new UserRegisterResponseDTO(
                userToRegister.getName(),
                userToRegister.getEmail(),
                userToRegister.getPhoneNo(),
                userToRegister.getId());
    }

    @Override
    public UserDetailResponseDTO getUserDetail(String email) {
        UserDetailProjection user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return new UserDetailResponseDTO(user.getName(), user.getEmail(), user.getPhoneNo());
    }

    @Override
    public User sendVerificationCode(String email, String password) {

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        String uuid = UUID.randomUUID().toString();
        user.setVerificationCode(uuid.substring(uuid.length() - 6));

        user.setExpiredTime(LocalDateTime.now().plusMinutes(5));

        userRepository.save(user);

        return user;

    }
}
