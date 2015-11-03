package com.yksong.bluedit.app;

import android.app.Application;

import com.yksong.bluedit.component.AppComponent;
import com.yksong.bluedit.component.AppModule;
import com.yksong.bluedit.component.DaggerAppComponent;

/**
 * Created by esong on 2015-11-01.
 */
public class BlueDitApp extends Application {
    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
