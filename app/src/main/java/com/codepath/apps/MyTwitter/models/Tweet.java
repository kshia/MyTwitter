package com.codepath.apps.MyTwitter.models;

import android.text.format.DateUtils;

import com.codepath.apps.MyTwitter.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@Parcel
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

    public long getLikeCount() {
        return likeCount;
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String relTimeStamp;
    public long likeCount;
    public long retweetCount;
    public String timeStamp;
    public String mediaUrl;

    public Tweet() {

    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.relTimeStamp = getRelativeTimeAgo(tweet.createdAt);
            tweet.likeCount = jsonObject.getLong("favorite_count");
            tweet.retweetCount = jsonObject.getLong("retweet_count");
            tweet.timeStamp = TimeFormatter.getTimeStamp(tweet.createdAt);
            JSONObject entities = jsonObject.getJSONObject("entities");
            if (entities != null) {
                JSONArray media = entities.getJSONArray("media");
                if (media != null) {
                    tweet.mediaUrl = media.getJSONObject(0).getString("media_url");
                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;

    }

    public static String getRelativeTimeAgo(String rawJsonDate) {

        return TimeFormatter.getTimeDifference(rawJsonDate);

//        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
//        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
//        sf.setLenient(true);
//
//        String relativeDate = "";
//        try {
//            long dateMillis = sf.parse(rawJsonDate).getTime();
//            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
//                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
//
//            if (relativeDate.indexOf("hour") > 0) {
//                String cut = relativeDate.substring(0, relativeDate.indexOf("hour") + 1);
//                return cut.replace(" ", "");
//            }
//
//            if (relativeDate.indexOf("second") > 0) {
//                String cut = relativeDate.substring(0, relativeDate.indexOf("second") + 1);
//                return cut.replace(" ", "");
//            }
//
//            if (relativeDate.indexOf("minute") > 0) {
//                String cut = relativeDate.substring(0, relativeDate.indexOf("minute") + 1);
//                return cut.replace(" ", "");
//            }
//
//            if (relativeDate.indexOf("day") > 0) {
//                String cut = relativeDate.substring(0, relativeDate.indexOf("day") + 1);
//                return cut.replace(" ", "");
//            }
//
//            if (relativeDate.indexOf(",") > 0) {
//                String cut = relativeDate.substring(0, relativeDate.indexOf(","));
//                return cut;
//            }
//
//            if (relativeDate.indexOf("in") > 0) {
//                return "now";
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return relativeDate;
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
