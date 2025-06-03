package com.tix.nostra.nostra_tix.projection;

import java.math.BigDecimal;

public interface SeatByStudioIdProjection {

    Long getId();

    String getSeatNumber();

    Integer getRowIndex();

    Integer getColumnIndex();

    String getSeatTypeName();

    BigDecimal getAdditionalPrice();

    Boolean getAvailable();

    Boolean getVisible();

}
