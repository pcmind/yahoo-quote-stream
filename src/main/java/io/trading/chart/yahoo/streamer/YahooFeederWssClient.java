package io.trading.chart.yahoo.streamer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.org.yahoo.proto.Yahoo;


public class YahooFeederWssClient extends org.java_websocket.client.WebSocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(YahooFeederWssClient.class);

    private static final String WEBSOCKET_URI = "wss://streamer.finance.yahoo.com/";

    private final List<Consumer<Yahoo.StreamerData>> onMessage = new CopyOnWriteArrayList<>();
    private final List<Consumer<Throwable>> onClose = new CopyOnWriteArrayList<>();
    private final List<Consumer<YahooFeederWssClient>> onOpen = new CopyOnWriteArrayList<>();


    private YahooFeederWssClient(URI serverUri) {
        super(serverUri);
    }

    public static YahooFeederWssClient create() throws IOException {
        final URI uri;
        try {
            uri = new URI(WEBSOCKET_URI);
        } catch (URISyntaxException e) {
            throw new IOException("Impossible to fail");
        }
        return new YahooFeederWssClient(uri);
    }

    public void addMessageListener(Consumer<Yahoo.StreamerData> listener) {
        onMessage.add(listener);
    }

    public void removeMessageListener(Consumer<Yahoo.StreamerData> listener) {
        onMessage.remove(listener);
    }

    public void addOnCloseListener(Consumer<Throwable> onCloseListener) {
        onClose.add(onCloseListener);
    }

    public void removeOnCloseListener(Consumer<Throwable> onCloseListener) {
        onClose.remove(onCloseListener);
    }

    public void addOnOpenListener(Consumer<YahooFeederWssClient> onCloseListener) {
        onOpen.add(onCloseListener);
    }

    public void removeOnOpenListener(Consumer<YahooFeederWssClient> onCloseListener) {
        onOpen.remove(onCloseListener);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.info("OnOpen {}", serverHandshake);
        onOpen.forEach(e -> e.accept(this));
    }

    @Override
    public void onMessage(String s) {
        try {
            final byte[] decode = Base64.getDecoder().decode(s);
            final Yahoo.StreamerData data = Yahoo.StreamerData.parseFrom(decode);
            LOGGER.trace("Message {}", data);
            onMessage.forEach(c -> c.accept(data));
        } catch (Exception e) {
            finish(e);
            throw new RuntimeException(e);
        }
    }

    private void finish(Exception e) {
        if(e != null) {
            LOGGER.error("Error finish", e);
        }else{
            LOGGER.debug("finish");
        }
        final List<Consumer<Throwable>> consumers = new ArrayList<>(onClose);
        onClose.clear();
        consumers.forEach(c -> c.accept(e));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LOGGER.trace("close {}, {}, {}", code, reason, remote);
        if (code != CloseFrame.NORMAL) {
            finish(new RuntimeException("failed code:" + code + ", reason: " + reason + ", remote:" + reason));
        }
        finish(null);
    }

    @Override
    public void onError(Exception e) {
        finish(e);
    }

    public void subscribe(Collection<String> symbol) {
        LOGGER.trace("subscribe {}", symbol);
        final List<String> collect =
            symbol.stream().map(e -> "\"" + e + "\"").collect(Collectors.toList());

        String concat = String.join(",", collect);
        send("{\"subscribe\":[" + concat + "]}");
    }
}
