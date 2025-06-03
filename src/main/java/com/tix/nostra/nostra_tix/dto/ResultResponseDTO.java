package com.tix.nostra.nostra_tix.dto;

public record ResultResponseDTO<T>(
        String message,
        T result) {
}
