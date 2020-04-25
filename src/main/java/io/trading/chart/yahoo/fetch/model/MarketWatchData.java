package io.trading.chart.yahoo.fetch.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public class MarketWatchData {
    private String symbol;
    private BigDecimal targetPrice;
    private BigDecimal quartersEstimate;
    private BigDecimal yearsEstimate;
    private BigDecimal medianPeOnCy;
    private BigDecimal nextFiscalYear;
    private BigDecimal medianPeNextFy;
    private BigDecimal lastQuarterEarnings;
    private BigDecimal yearAgoEarnings;
    private String recommendation; // ENUM?
    private Integer numberOfRatings;
    private Integer buy;
    private Integer overweight;
    private Integer hold;
    private Integer underweight;
    private Integer sell;
    private String history;
    private LocalDate lastUpdated;
}
