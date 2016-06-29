package com.codepath.apps.MyTwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.MyTwitter.TwitterApplication;
import com.codepath.apps.MyTwitter.TwitterClient;
import com.codepath.apps.MyTwitter.activities.ProfileActivity;
import com.codepath.apps.MyTwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kshia on 6/27/16.
 */
public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userFragment.setArguments(args);
        return userFragment;
    }

    public void populateTimeline() {
        String screenName = getArguments().getString("screen_name");

        ((ProfileActivity) getActivity()).showProgressBar();
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                clear();
                addAll(Tweet.fromJSONArray(json));
                Log.d("APICALL", toString());
                finishedRefreshing();
                ((ProfileActivity) getActivity()).hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("APICALL", errorResponse.toString());
            }
        });
    }
}
