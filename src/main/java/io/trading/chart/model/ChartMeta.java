package io.trading.chart.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Value;

@Value
public class ChartMeta {
    private String currency;
    private String exchangeName; //": "NMS",
    private String instrumentType; //": "EQUITY",
    private ZoneOffset offset;
    private double currentPrice;
    private Granularity dataGranularity; //"1m","5m", "15m", "30m", "4h", "1d", "1w", "1m", "1y"
    private LocalDateTime lastUpdate;
}
