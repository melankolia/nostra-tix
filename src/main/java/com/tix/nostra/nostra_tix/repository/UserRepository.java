package com.tix.nostra.nostra_tix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tix.nostra.nostra_tix.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailOrPhoneNo(String email, String phoneNo);

    User findByEmail(String email);
}
