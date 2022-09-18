package com.github.zavier.flow.rxjava;

import com.github.zavier.flow.TempInfo;
import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        final Observable<TempInfo> observable = TempObserver.getCelsiusTemperatures("New York", "Chicago", "San Francisco");
        observable.blockingSubscribe(new TempObserver());
    }

}
