package com.tix.nostra.nostra_tix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.tix.nostra.nostra_tix.validator.EndDateAfterStartDate;

@EndDateAfterStartDate()
public record MovieRequestDTO(
        @NotBlank(message = "Name is required") String name,

        @NotBlank(message = "Trailer URI is required") String trailerURI,

        @NotBlank(message = "Image URI is required") String imageURI,

        @NotNull(message = "Duration is required") Integer duration,

        @NotBlank(message = "Synopsis is required") String synopsis,

        @NotNull(message = "Showing start date is required") Long showingStartDate,

        @NotNull(message = "Showing end date is required") Long showingEndDate) {
}