package com.tix.nostra.nostra_tix.service;

import com.tix.nostra.nostra_tix.domain.User;
import com.tix.nostra.nostra_tix.dto.UserLoginDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterRequestDTO;
import com.tix.nostra.nostra_tix.dto.UserRegisterResponseDTO;

public interface UserService {
    UserLoginDTO login(UserLoginDTO userLoginDTO);

    UserRegisterResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO);
}
