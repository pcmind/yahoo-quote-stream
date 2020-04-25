# yahoo quote stream

Lib to access yahoo chart quote API and live quotes subscription.

#Dependencies
This project dependends on protobuff to generate classes to parse live quotes.


# How to

Add maven dependency to your project:
```
<dependency>
    <groupId>io.github.pcmind.yahoo</groupId>
    <artifactId>yahoo-quote-stream</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Get chart data: 
```
    final YahooQuoteFetcher yqf = new YahooQuoteFetcher();
    // get last 2 days of 1m quotes for symbol CLVS
    final YahooResult yahooResult = yqf.fetch("CLVS", "1m", 2);
```

Get live quote:
```
    final YahooFeederWssClient client = YahooFeederWssClient.create();
     s.addOnOpenListener(/* add on open listener here */);
    s.addMessageListener(/* add on message received listener here */);
    s.addOnCloseListener(/* add on session close listener here */);
    s.connect(); //this operation is asynchronous
    //subscribe to live quotes of specified symbol
    s.subscribe("CLVS");
```