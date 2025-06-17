package com.tix.nostra.nostra_tix.projection;

import java.time.LocalDateTime;

public interface UserDetailProjection {

    Long getId();

    String getName();

    String getEmail();

    String getPhoneNo();

    String getPassword();

    LocalDateTime getExpiredTime();

    String getVerificationCode();
}
