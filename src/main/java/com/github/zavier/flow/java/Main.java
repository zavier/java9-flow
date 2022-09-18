package com.github.zavier.flow.java;

import com.github.zavier.flow.TempInfo;

import java.util.concurrent.Flow;

public class Main {
    public static void main(String[] args) {
//        getTemperatures("New York").subscribe(new TempSubscriber());
        getCelsiusTemperatures("Bei Jing").subscribe(new TempSubscriber());
    }

    private static Flow.Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            final TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }

    private static Flow.Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }
}
