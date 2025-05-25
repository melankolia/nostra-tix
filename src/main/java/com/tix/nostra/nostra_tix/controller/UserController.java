package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.UserLoginDTO;
import com.tix.nostra.nostra_tix.dto.UserLoginResponseDTO;
import com.tix.nostra.nostra_tix.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {

            userService.login(userLoginDTO);

            return ResponseEntity.ok(new UserLoginResponseDTO("success", "Login successful"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new UserLoginResponseDTO("error", e.getMessage()));
        }
    }
}
