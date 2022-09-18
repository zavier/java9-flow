package com.github.zavier.flow.rxjava;

import com.github.zavier.flow.TempInfo;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 订阅者（观察者）
 */
public class TempObserver implements Observer<TempInfo> {

    public static Observable<TempInfo> getCelsiusTemperatures(String... town) {
        return Observable.merge(Arrays.stream(town).map(TempObserver::getCelsiusTemperature).collect(Collectors.toList()));
    }

    public static Observable<TempInfo> getNegativeTemperature(String town) {
        return getCelsiusTemperature(town)
                .filter(tempInfo ->  tempInfo.getTemp() < 0);
    }

    public static Observable<TempInfo> getCelsiusTemperature(String town) {
        return getTemperature(town)
                .map(tempInfo -> new TempInfo(tempInfo.getTown(), (tempInfo.getTemp() - 32) * 5 / 9));
    }

    public static Observable<TempInfo> getTemperature(String town) {
        return Observable.create(emitter ->
                Observable.interval(1, TimeUnit.SECONDS)
                        .subscribe(i -> {
                            if (!emitter.isDisposed()) {
                                if (i >= 5) {
                                    emitter.onComplete();
                                } else {
                                    try {
                                        emitter.onNext(TempInfo.fetch(town));
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }
                        }));
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull TempInfo tempInfo) {
        System.out.println(tempInfo);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        System.out.println("Got problem:" + e.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}
