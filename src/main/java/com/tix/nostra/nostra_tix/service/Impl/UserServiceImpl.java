package com.tix.nostra.nostra_tix.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.domain.User;
import com.tix.nostra.nostra_tix.dto.UserDetailResponseDTO;
import com.tix.nostra.nostra_tix.dto.UserLoginDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterRequestDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterResponseDTO;
import com.tix.nostra.nostra_tix.exception.ResourceNotFoundException;
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
            return new UserLoginDTO(user.getEmail(), user.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Error during login process", e);
        }
    }

    @Override
    public UserRegisterResponseDTO register(UserRegisterRequestDTO user) {

        User userToRegister = new User();
        userToRegister.setName(user.name());
        userToRegister.setEmail(user.email());
        userToRegister.setPhoneNo(user.phoneNo());
        userToRegister.setPassword(user.password());

        User userRegistered = userRepository.save(userToRegister);
        return new UserRegisterResponseDTO(
                userRegistered.getName(),
                userRegistered.getEmail(),
                userRegistered.getPhoneNo());
    }

    @Override
    public UserDetailResponseDTO getUserDetail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return new UserDetailResponseDTO(user.getName(), user.getEmail(), user.getPhoneNo());
    }
}
