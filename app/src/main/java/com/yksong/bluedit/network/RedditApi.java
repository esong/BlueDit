package com.yksong.bluedit.network;

import com.yksong.bluedit.model.RedditApiResult;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by esong on 2015-11-01.
 */
public interface RedditApi {
    @GET("/r/dota2.json?limit=50")
    Observable<RedditApiResult> subredditListing(@Query("after") String after);
}
