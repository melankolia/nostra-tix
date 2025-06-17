package com.tix.nostra.nostra_tix.validator;

import com.tix.nostra.nostra_tix.dto.MovieRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, MovieRequestDTO> {

    @Override
    public boolean isValid(MovieRequestDTO movieRequestDTO, ConstraintValidatorContext context) {
        return movieRequestDTO.showingEndDate() > movieRequestDTO.showingStartDate();
    }
}