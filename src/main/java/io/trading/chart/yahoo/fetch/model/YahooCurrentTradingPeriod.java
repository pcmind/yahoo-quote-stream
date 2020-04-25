package io.trading.chart.yahoo.fetch.model;

import lombok.Data;

@Data
public class YahooCurrentTradingPeriod {
    private YahooTradingPeriod pre;
    private YahooTradingPeriod regular;
    private YahooTradingPeriod post;
}
