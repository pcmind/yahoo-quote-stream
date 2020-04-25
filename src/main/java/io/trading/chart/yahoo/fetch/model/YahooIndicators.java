package io.trading.chart.yahoo.fetch.model;

import java.util.List;

import lombok.Data;

@Data
public class YahooIndicators {
    private List<YahooQuote> quote;
    private List<YahooUnadjClose> unadjclose;
    private List<YahooAdjClose> adjclose;
}
