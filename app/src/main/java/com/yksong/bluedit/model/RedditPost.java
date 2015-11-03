package com.yksong.bluedit.model;

/**
 * Created by esong on 2015-11-02.
 */
public class RedditPost {
    String kind;
    Data data;

    public class Data {
        String domain;
        String author;
        String score;
        long created_utc;
        String url;
        String title;
        String link_flair_text;
        String name;

        public String getDomain() {
            return domain;
        }

        public String getAuthor() {
            return author;
        }

        public String getScore() {
            return score;
        }

        public long getCreatedTime() {
            return created_utc;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public String getLink_flair_text() {
            return link_flair_text;
        }

        public String getName() {
            return name;
        }

    }

    public Data getData() {
        return data;
    }
}
