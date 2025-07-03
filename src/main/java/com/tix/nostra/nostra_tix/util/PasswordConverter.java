package com.tix.nostra.nostra_tix.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
public class PasswordConverter implements AttributeConverter<String, String> {

    private static EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        PasswordConverter.encryptionService = encryptionService;
    }

    @Override
    public String convertToDatabaseColumn(String password) {
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        try {
            // Hash the password before storing
            return encryptionService.hashPassword(password);
        } catch (Exception e) {
            // If hashing fails, return the original value (for backward compatibility)
            return password;
        }
    }

    @Override
    public String convertToEntityAttribute(String hashedPassword) {
        // Return the hashed password as-is (we don't decrypt BCrypt hashes)
        return hashedPassword;
    }
}