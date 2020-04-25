# yahoo quote stream

Unofficial lib to access yahoo chart quote API and live quotes subscription.

#Dependencies
This project dependends on protobuff to generate classes to parse live quotes.


# How to

Use [Jitpack](https://jitpack.io/) repo.
 
Add maven dependency to your project:
```
<dependency>
    <groupId>com.github.pcmind</groupId>
    <artifactId>yahoo-quote-stream</artifactId>
    <version>master-SNAPSHOT</version>
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