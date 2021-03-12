package com.hvt.booking_lux.model.statistics;

import java.time.ZonedDateTime;

public interface ResObjectYearStatistics {

    long getId();
    String getName();
    int getMonth();
    int getNum();
    int getTotal();

}
