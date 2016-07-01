package com.codepath.apps.MyTwitter.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by kshia on 6/27/16.
 */
@Parcel
public class User {
    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

//    public long getLikeCount() {
//        return likeCount;
//    }
//
//    public long getRetweetCount() {
//        return retweetCount;
//    }

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public String tagLine;
    public int followerCount;
    public int followingCount;
//    public long likeCount;
//    public long retweetCount;

    public User() {

    }

    public static User fromJSON(JSONObject jsonObject) {
        User u = new User();

        try {
            u.name = jsonObject.getString("name");
            u.uid = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.tagLine = jsonObject.getString("description");
            u.followerCount = jsonObject.getInt("followers_count");
            u.followingCount = jsonObject.getInt("friends_count");
//            u.likeCount = jsonObject.getLong("favourites_count");
//            u.retweetCount = jsonObject.getLong("retweet_count");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }
}
