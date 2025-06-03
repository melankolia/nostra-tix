package com.tix.nostra.nostra_tix.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.config.MinioProperties;
import com.tix.nostra.nostra_tix.service.FileService;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;

@Service
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public FileServiceImpl(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String path = "/" + minioProperties.getBucket() + "/" + file.getOriginalFilename();
        minioClient
                .putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket())
                        .object(file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType("video/mp4")
                        .build());
        return path;
    }

    @Override
    public String generatePresignedUrl(String fileName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .method(Method.PUT)
                .build();

        String url = minioClient.getPresignedObjectUrl(args);

        return url;
    }
}
