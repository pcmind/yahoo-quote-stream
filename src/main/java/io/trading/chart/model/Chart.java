package io.trading.chart.model;

import java.io.Closeable;
import java.util.List;

public interface Chart extends Closeable {
    String getSymbol();

    ChartMeta getMeta();

    List<Point> getPoints();

    void addListener(QuoteFeedListener listener);
}
