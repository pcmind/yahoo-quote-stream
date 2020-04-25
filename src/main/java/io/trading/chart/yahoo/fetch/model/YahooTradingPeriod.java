package io.trading.chart.yahoo.fetch.model;

import lombok.Data;

@Data
public class YahooTradingPeriod {
    private String timezone;
    private long end;
    private long start;
    private int gmtoffset;
}
