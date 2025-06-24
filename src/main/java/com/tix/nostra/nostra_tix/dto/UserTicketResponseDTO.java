package com.tix.nostra.nostra_tix.dto;

import java.util.List;

public record UserTicketResponseDTO(
                Long id,
                String movieName,
                String movieImageURI,
                String theaterName,
                String studioName,
                List<String> seatNumbers,
                String status) {
}
