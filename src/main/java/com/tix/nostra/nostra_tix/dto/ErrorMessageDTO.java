package com.tix.nostra.nostra_tix.dto;

import java.util.List;

public record ErrorMessageDTO(
        Integer code, List<String> message) {
}
