package com.yksong.bluedit.presenter;

import com.yksong.bluedit.model.RedditApiResult;
import com.yksong.bluedit.model.RedditPost;
import com.yksong.bluedit.network.RedditApi;
import com.yksong.bluedit.view.RedditView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by esong on 2015-11-01.
 */
@Singleton
public class RedditPresenter {
    private RedditView mView;
    @Inject RedditApi mApi;
    List<RedditPost> mPosts;
    boolean requesting;

    @Inject
    public RedditPresenter(){}

    public void takeView(RedditView view) {
        mView = view;
    }

    public void request() {
        requestPosts("");
    }

    public void requestAfter() {
        RedditPost.Data postData = mPosts.get(mPosts.size() - 1).getData();
        if (postData != null) {
            requestPosts(postData.getName());
        }
    }

    private void requestPosts(final String after) {
        if (!requesting) {
            requesting = true;
            mApi.subredditListing(after)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<RedditApiResult>() {
                        @Override
                        public void call(RedditApiResult redditApiResult) {
                            int changePos = 0;
                            if (after == "") {
                                mPosts = redditApiResult.getPosts();
                            } else {
                                changePos = mPosts.size();
                                mPosts.addAll(redditApiResult.getPosts());
                            }

                            requesting = false;
                            mView.onData(mPosts, changePos);
                        }
                    });
        }
    }
}
