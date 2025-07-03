package com.tix.nostra.nostra_tix.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptionService {

    @Value("${app.encryption.secret-key:nostra-tix-secret-key-2024}")
    private String secretKey;

    private final BCryptPasswordEncoder passwordEncoder;

    public EncryptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Hash password using BCrypt
     */
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Verify password against BCrypt hash
     */
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    /**
     * Encrypt OTP using AES
     */
    public String encryptOTP(String otp) {
        try {
            SecretKeySpec secretKeySpec = generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(otp.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting OTP", e);
        }
    }

    /**
     * Decrypt OTP using AES
     */
    public String decryptOTP(String encryptedOTP) {
        try {
            SecretKeySpec secretKeySpec = generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedOTP));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting OTP", e);
        }
    }

    /**
     * Generate AES key from secret key
     */
    private SecretKeySpec generateKey() throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        key = Arrays.copyOf(key, 16); // Use only first 128 bit
        return new SecretKeySpec(key, "AES");
    }
}