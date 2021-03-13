package com.hvt.booking_lux.model.statistics;

import com.hvt.booking_lux.model.enumeration.Sentiment;

public interface ReviewSentimentStatistics {

    long getId();
    Sentiment getSentiment();

}
