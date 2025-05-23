package com.tix.nostra.nostra_tix.dto;

import java.util.List;

public record ResultPageResponseDTO<T>(
                List<T> result,
                Integer pages,
                Long elements) {
}