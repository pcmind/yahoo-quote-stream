package io.trading.chart.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@Value(staticConstructor = "of")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Point {
    double open;
    double high;
    double low;
    @With
    double close;
    @With
    long volume;
    List<PointEvent> events;
    @EqualsAndHashCode.Include
    LocalDateTime time;
}

