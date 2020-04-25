package io.trading.chart.yahoo.fetch.model;

import lombok.Data;

@Data
public class YahooError {
    private String code;
    private String description;
}
