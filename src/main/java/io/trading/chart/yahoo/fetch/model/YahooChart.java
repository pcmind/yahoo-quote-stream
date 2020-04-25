package io.trading.chart.yahoo.fetch.model;

import java.util.List;

import lombok.Data;

@Data
public class YahooChart {
    List<YahooData> result;
    YahooError error;
}
