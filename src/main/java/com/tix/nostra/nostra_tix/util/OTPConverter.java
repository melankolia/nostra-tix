package com.tix.nostra.nostra_tix.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
public class OTPConverter implements AttributeConverter<String, String> {

    private static EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        OTPConverter.encryptionService = encryptionService;
    }

    @Override
    public String convertToDatabaseColumn(String otp) {
        if (otp == null || otp.trim().isEmpty()) {
            return null;
        }
        try {
            // Encrypt the OTP before storing
            return encryptionService.encryptOTP(otp);
        } catch (Exception e) {
            // If encryption fails, return the original value (for backward compatibility)
            return otp;
        }
    }

    @Override
    public String convertToEntityAttribute(String encryptedOTP) {
        if (encryptedOTP == null || encryptedOTP.trim().isEmpty()) {
            return null;
        }
        try {
            // Decrypt the OTP when reading from database
            return encryptionService.decryptOTP(encryptedOTP);
        } catch (Exception e) {
            // If decryption fails, return the original value (for backward compatibility)
            // This handles cases where the data might not be encrypted yet
            return encryptedOTP;
        }
    }
}