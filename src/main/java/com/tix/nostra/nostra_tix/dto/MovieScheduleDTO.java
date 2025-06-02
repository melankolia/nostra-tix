package com.tix.nostra.nostra_tix.dto;

import java.util.Date;

public record MovieScheduleDTO(
                Long id,
                String title,
                String imageURI,
                Date showTime,
                Date endShowTime,
                String theaterName,
                String studioName) {
}
