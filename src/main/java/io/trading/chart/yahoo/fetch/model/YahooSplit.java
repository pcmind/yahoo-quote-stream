package io.trading.chart.yahoo.fetch.model;

import lombok.Data;

@Data
public class YahooSplit {
    private Long date;
    private Integer numerator;
    private Integer denominator;
    private String splitRatio;
}
