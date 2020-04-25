package io.trading.chart.model;

public interface QuoteFeedListener {
    void onAddPoint(ChartMeta meta, Point point);

    void onUpdatedPoint(ChartMeta meta, Point oldPoint, Point newPoint);

    default void onChangeMeta(ChartMeta meta) {
        //duplicate of points change
    }
}
