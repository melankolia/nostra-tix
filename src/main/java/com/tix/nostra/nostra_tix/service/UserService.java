package com.tix.nostra.nostra_tix.service;

import com.tix.nostra.nostra_tix.dto.UserLoginDTO;

public interface UserService {
    UserLoginDTO login(UserLoginDTO userLoginDTO);
}
