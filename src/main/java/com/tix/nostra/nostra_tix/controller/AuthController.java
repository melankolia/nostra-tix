package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterRequestDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterResponseDTO;
import com.tix.nostra.nostra_tix.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResultResponseDTO<UserRegisterResponseDTO>> register(
            @RequestBody @Valid UserRegisterRequestDTO user) {

        UserRegisterResponseDTO registeredUser = userService.register(user);

        ResultResponseDTO<UserRegisterResponseDTO> resultResponseDTO = new ResultResponseDTO<>("OK", registeredUser);
        return ResponseEntity.ok(resultResponseDTO);
    }
}
