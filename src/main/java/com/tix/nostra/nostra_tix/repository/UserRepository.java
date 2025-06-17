package com.tix.nostra.nostra_tix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tix.nostra.nostra_tix.domain.User;
import com.tix.nostra.nostra_tix.projection.UserDetailProjection;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailOrPhoneNo(String email, String phoneNo);

    @Query("""
            SELECT u.id as id,
                   u.name as name,
                   u.email as email,
                   u.phoneNo as phoneNo,
                   u.password as password,
                   u.expiredTime as expiredTime,
                   u.verificationCode as verificationCode
            FROM User u
            WHERE u.email = :email
            """)
    UserDetailProjection findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
