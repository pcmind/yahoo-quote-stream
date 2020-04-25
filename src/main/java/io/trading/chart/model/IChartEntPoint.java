package io.trading.chart.model;

import java.io.Closeable;
import java.io.IOException;

public interface IChartEntPoint extends Closeable {
    default Chart get1mChart(String symbol) throws IOException {
        return getChart(symbol, Granularity._1m, 2);
    }

    default Chart get4hChart(String symbol) throws IOException {
        return getChart(symbol, Granularity._4h, 160);
    }

    public Chart getChart(String symbol, Granularity granularity, int maxDays) throws IOException;
}
