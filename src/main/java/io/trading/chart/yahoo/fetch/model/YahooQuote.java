package io.trading.chart.yahoo.fetch.model;

import lombok.Data;

import java.util.List;

@Data
public class YahooQuote {
    private List<Double> open;
    private List<Double> high;
    private List<Double> low;
    private List<Double> close;
    private List<Long> volume;

    public double getOpen(int index) {
        return getOrDefault(open, index, 0d);
    }

    private static <T> T getOrDefault(List<T>  t, int index, T defaultV) {
        final T v = t.get(index);
        return v == null ? defaultV : v;
    }

    public double getHigh(int index) {
        return getOrDefault(high, index, 0d);
    }

    public double getLow(int index) {
        return getOrDefault(low, index, 0d);
    }

    public double getClose(int index) {
        return getOrDefault(close, index, 0d);
    }

    public long getVolume(int index) {
        return getOrDefault(volume, index, 0L);
    }
}
