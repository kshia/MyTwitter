package com.codepath.apps.MyTwitter.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Tweet {
    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRelTimeStamp() {
        return relTimeStamp;
    }

    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String relTimeStamp;

//    public Tweet() {
//
//    }
//
//
//    // Fake tweet to display after just posted
//    public Tweet(String mBody, User mUser) {
//        body = mBody;
//        user = mUser;
//        relTimeStamp = "now";
//    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.relTimeStamp = getRelativeTimeAgo(tweet.createdAt);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;

    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            try {
                JSONObject tweetJson = json.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }
}
