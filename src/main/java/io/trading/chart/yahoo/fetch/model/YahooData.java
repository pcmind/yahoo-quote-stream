package io.trading.chart.yahoo.fetch.model;

import java.util.List;

import lombok.Data;

@Data
public class YahooData {
    YahooMeta meta;
    List<Long> timestamp;
    YahooIndicators indicators;
    YahooEvents events;
}
