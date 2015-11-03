package com.yksong.bluedit.component;

import com.yksong.bluedit.view.RedditView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by esong on 2015-11-01.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    public void inject(RedditView view);
}
