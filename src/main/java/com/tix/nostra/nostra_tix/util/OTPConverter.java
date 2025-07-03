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
        if (otp == null) {
            return null;
        }
        // Encrypt the OTP before storing
        return encryptionService.encryptOTP(otp);
    }

    @Override
    public String convertToEntityAttribute(String encryptedOTP) {
        if (encryptedOTP == null) {
            return null;
        }
        // Decrypt the OTP when reading from database
        return encryptionService.decryptOTP(encryptedOTP);
    }
}