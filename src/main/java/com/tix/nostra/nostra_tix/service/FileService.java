package com.tix.nostra.nostra_tix.service;

import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.dto.PresignedURLResponseDTO;

public interface FileService {
    public String uploadFile(MultipartFile file) throws Exception;

    public PresignedURLResponseDTO generatePresignedUrl(String fileName) throws Exception;
}
