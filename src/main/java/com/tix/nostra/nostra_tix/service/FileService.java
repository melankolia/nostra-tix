package com.tix.nostra.nostra_tix.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String uploadFile(MultipartFile file) throws Exception;

    public String generatePresignedUrl(String fileName) throws Exception;
}
