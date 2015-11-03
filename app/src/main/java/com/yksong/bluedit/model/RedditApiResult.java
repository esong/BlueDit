package com.yksong.bluedit.model;

import java.util.List;

/**
 * Created by esong on 2015-11-02.
 */
public class RedditApiResult {
    String kind;
    Data data;

    public class Data {
        List<RedditPost> children;
        String after;
    }

    public List<RedditPost> getPosts() {
        if (data != null) {
            return data.children;
        }
        return null;
    }
}
