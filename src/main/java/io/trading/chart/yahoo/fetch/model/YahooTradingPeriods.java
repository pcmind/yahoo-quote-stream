package io.trading.chart.yahoo.fetch.model;

import lombok.Data;

@Data
public class YahooTradingPeriods {
    private YahooTradingPeriod[][] pre;
    private YahooTradingPeriod[][] regular;
    private YahooTradingPeriod[][] post;
}
