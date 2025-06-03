package com.tix.nostra.nostra_tix.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateMovieImagePresignedDTO(
        @NotBlank String imageURI) {
}
