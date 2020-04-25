package io.trading.chart.yahoo.fetch.model;

import java.util.Map;

import lombok.Data;

@Data
public class YahooEvents {
    private Map <Long, YahooDividend> dividends;
    private Map <Long, YahooSplit> splits;
}
