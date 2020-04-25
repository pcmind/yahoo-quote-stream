package io.trading.chart.model;

public enum Granularity {
    _1m,
    _2m,
    _5m,
    _15m,
    _30m,
    _1h,
    _2h,
    _4h,
    _1d;

    public static Granularity fromString(String name) {
        return valueOf("_"+name);
    }

    public String getString() {
        return name().substring(1);
    }
}
