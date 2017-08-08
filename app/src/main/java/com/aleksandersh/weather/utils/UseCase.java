package com.aleksandersh.weather.utils;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

public abstract class UseCase<T> {

    private CompositeDisposable disposables;

    public void execute(Observable<T> observable, DisposableObserver<T> observer) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(observer));
    }

    public void execute(Single<T> single, DisposableSingleObserver<T> observer) {
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(single.subscribeWith(observer));
    }

    public void execute(Flowable<T> flowable, DisposableSubscriber<T> subscriber) {
        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(flowable.subscribeWith(subscriber));
    }

    public void dispose() {
        if (!disposables.isDisposed()) disposables.dispose();
    }

}
