package com.tix.nostra.nostra_tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.dto.FileResourceResponseDTO;
import com.tix.nostra.nostra_tix.dto.PresignedURLResponseDTO;
import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
import com.tix.nostra.nostra_tix.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileResourceController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<ResultResponseDTO<FileResourceResponseDTO>> uploadFile(
            @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.uploadFile(file);

            FileResourceResponseDTO responseDTO = new FileResourceResponseDTO(filePath);
            ResultResponseDTO<FileResourceResponseDTO> resultResponseDTO = new ResultResponseDTO<>(
                    "OK",
                    responseDTO);

            return ResponseEntity.ok(resultResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResultResponseDTO<>(
                            "Failed to upload file: " + e.getMessage(),
                            null));
        }
    }

    @GetMapping("/presigned-url")
    public ResponseEntity<ResultResponseDTO<PresignedURLResponseDTO>> generatePresignedUrl(
            @RequestParam("fileName") String fileName)
            throws Exception {

        PresignedURLResponseDTO filePath = fileService.generatePresignedUrl(fileName);

        ResultResponseDTO<PresignedURLResponseDTO> responseDTO = new ResultResponseDTO<>(
                "OK",
                new PresignedURLResponseDTO(filePath.url(),
                        filePath.fullPath()));

        return ResponseEntity.ok(responseDTO);
    }
}