package com.github.zavier.flow.java;

import com.github.zavier.flow.TempInfo;

import java.util.concurrent.Flow;

/**
 * 订阅者
 */
public class TempSubscriber implements Flow.Subscriber<TempInfo> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(TempInfo item) {
        System.out.println(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}
