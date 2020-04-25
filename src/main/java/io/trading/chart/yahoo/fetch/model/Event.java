package io.trading.chart.yahoo.fetch.model;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public class Event {
    private EventType eventType;
    private BigDecimal amount;
    private Integer numerator;
    private Integer denominator;
}
