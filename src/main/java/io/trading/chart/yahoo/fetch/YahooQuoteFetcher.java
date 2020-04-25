package io.trading.chart.yahoo.fetch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import io.trading.chart.yahoo.fetch.model.YahooResult;
import io.trading.chart.yahoo.fetch.model.YahooTradingPeriod;
import io.trading.chart.yahoo.fetch.model.YahooTradingPeriods;
import lombok.Value;

public class YahooQuoteFetcher {
    private static final String FINANCE_CHART = "https://query1.finance.yahoo.com/v8/finance/chart/";
    private final Gson decoder;

    public YahooQuoteFetcher() {
        decoder = new GsonBuilder()
            .registerTypeAdapter(YahooTradingPeriods.class, new JsonDeserializer<YahooTradingPeriods>() {
                @Override
                public YahooTradingPeriods deserialize(
                    JsonElement json, Type typeOfT, JsonDeserializationContext context
                ) throws JsonParseException {
                    if (json.isJsonNull()) {
                        return null;
                    } else if (json.isJsonArray()) {
                        //if request does not specify to get pre/post trading period response lack intermediate object
                        final YahooTradingPeriods yahooTradingPeriods = new YahooTradingPeriods();
                        yahooTradingPeriods.setRegular(deserializeTradingPeriod(json, context));
                        return yahooTradingPeriods;
                    } else {
                        //if request does request pre/post trading session; pre/post may still be null be intermediate object is present
                        final YahooTradingPeriods yahooTradingPeriods = new YahooTradingPeriods();
                        final JsonObject object = json.getAsJsonObject();
                        yahooTradingPeriods.setPre(deserializeTradingPeriod(object.get("pre"), context));
                        yahooTradingPeriods.setRegular(deserializeTradingPeriod(object.get("regular"), context));
                        yahooTradingPeriods.setPost(deserializeTradingPeriod(object.get("post"), context));
                        return yahooTradingPeriods;
                    }
                }

                private YahooTradingPeriod[][] deserializeTradingPeriod(
                    JsonElement json,
                    JsonDeserializationContext context
                ) {
                    return context.deserialize(json, YahooTradingPeriod[][].class);
                }
            })
            .create();
    }

    public static void main(String[] args) throws IOException {
        final YahooResult yahooResult = new YahooQuoteFetcher().fetch("CLVS", "1m", 2, true);
        System.out.println(yahooResult);
    }

    /**
     * @param symbol
     * @param interval       1m, 5m, 15m, 30m, 1h, 4h, 1d, 1
     * @param daysBack
     * @param includePrePost
     * @return
     */
    public YahooResult fetch(String symbol, String interval, Integer daysBack, boolean includePrePost)
        throws IOException {
        final Date time = Calendar.getInstance().getTime();
        time.setTime(time.getTime() - TimeUnit.DAYS.toMillis(daysBack));
        return decoder.fromJson(
            fetchHttps(
                get(
                    symbol,
                    time,
                    Calendar.getInstance().getTime(),
                    interval,
                    includePrePost
                ),
                "https://finance.yahoo.com/quote/" + symbol + "/chart?p=" + symbol),
            YahooResult.class
        );
    }

    private String get(String symbol, java.util.Date from, java.util.Date to, String interval, boolean includePrePost) {
        final QueryBuilder q = new QueryBuilder(FINANCE_CHART);
        q.prams.add(symbol);
        //CLVS?symbol=CLVS&period1=1575984950&period2=1576157750&interval=1m&includePrePost=true&events=div%7Csplit%7Cearn&lang=en-US&region=US&crumb=Exq9QZqOv2w&corsDomain=finance.yahoo.com
        q.query("symbol", symbol);
        q.query("period1", String.valueOf(from.getTime() / 1000));
        q.query("period2", String.valueOf(to.getTime() / 1000));
        q.query("interval", interval);
        q.query("includePrePost", String.valueOf(includePrePost));
        q.query("events", "div|split|earn|event");
        q.query("lang", "en-US");
        q.query("region", "US");
        q.query("crumb", "Exq9QZqOv2w");
        q.query("corsDomain", "finance.yahoo.com");
        return q.build();
    }

    private static class QueryBuilder {
        private final String baseUrl;
        private List<String> prams = new ArrayList<>();
        private List<Pair> query = new ArrayList<>();

        public QueryBuilder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String build() {
            return baseUrl + Joiner.on("/").join(prams) + "?" + Joiner.on("&").join(query);
        }

        public QueryBuilder query(String name, String value) {
            query.add(new Pair(name, value));
            return this;
        }
    }

    @Value
    private static class Pair {
        String name;
        String value;

        public Pair(String name, String value) {
            this.name = name;
            this.value = encodeValue(value);
        }

        // Method to encode a string value using `UTF-8` encoding scheme
        private static String encodeValue(String value) {
            try {
                return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex.getCause());
            }
        }

        @Override
        public String toString() {
            return name + "=" + value;
        }
    }


    private static String fetchHttps(String url, String referer) throws IOException {
        final HttpGet get = new HttpGet(url);
        get.addHeader(new BasicHeader("Referer", referer));
        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(get)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Unable to load" + response.getStatusLine());
            }
            final String s =
                CharStreams.toString(new InputStreamReader(response.getEntity().getContent(), Charsets.UTF_8));
            return s;
        }
    }
}
