package com.yksong.bluedit.component;

import android.app.Application;

import com.yksong.bluedit.app.BlueDitApp;
import com.yksong.bluedit.network.RedditApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by esong on 2015-11-01.
 */
@Module
public class AppModule {
    RedditApi mApi;

    public AppModule(BlueDitApp app) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApi = retrofit.create(RedditApi.class);
    }

    @Provides
    @Singleton
    public RedditApi provideRedditApi() {
        return mApi;
    }
}
