package io.trading.chart.yahoo.fetch.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class YahooMeta {
    private String currency; //USD
    private String symbol; //CLVS
    private String exchangeName; //": "NMS",
    private String instrumentType; //": "EQUITY",
    private long firstTradeDate; //": 863683200,
    private int gmtoffset; //": -18000,
    private String timezone; //": "EST",
    private String exchangeTimezoneName; //": "America/New_York",
    private BigDecimal chartPreviousClose; //": 1177.62,
    private BigDecimal previousClose; //": 1461.76,
    private int scale; //": 3,
    private YahooCurrentTradingPeriod currentTradingPeriod;
    private YahooTradingPeriods tradingPeriods;
    private String dataGranularity; //"1m","5m", "15m", "30m", "4h", "1d", "1w", "1m", "1y"
    private List<String> validRanges; // ["1d","5d","1mo","3mo","6mo","1y","2y","5y","10y","ytd","max"]
}
