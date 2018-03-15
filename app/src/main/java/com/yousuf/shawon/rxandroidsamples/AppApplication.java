package com.yousuf.shawon.rxandroidsamples;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by yousuf on 3/15/18.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }
}
