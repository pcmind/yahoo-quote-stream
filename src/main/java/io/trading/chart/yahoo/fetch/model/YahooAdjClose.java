package io.trading.chart.yahoo.fetch.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class YahooAdjClose {
    private List<BigDecimal> adjclose;
}
