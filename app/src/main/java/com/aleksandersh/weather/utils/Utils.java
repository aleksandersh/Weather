package com.aleksandersh.weather.utils;


import java.util.Calendar;
import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by Vladimir Kondenko on 09.08.17.
 */

public class Utils {

    public static boolean areOnSameDay(int timestamp1, int timestamp2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date(timestamp1));
        cal2.setTime(new Date(timestamp2));
        boolean result = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        Timber.i("Dates fall on the same day - " + result);
        return result;
    }

    public static void transaction(Action action) {
        Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

}
