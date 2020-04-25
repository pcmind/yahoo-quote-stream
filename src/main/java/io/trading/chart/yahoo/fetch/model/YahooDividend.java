package io.trading.chart.yahoo.fetch.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class YahooDividend {
    private BigDecimal amount;
    private Long date;
}
