package com.codepath.apps.MyTwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.MyTwitter.TwitterApplication;
import com.codepath.apps.MyTwitter.TwitterClient;
import com.codepath.apps.MyTwitter.activities.TimelineActivity;
import com.codepath.apps.MyTwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kshia on 6/27/16.
 */
public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    public void populateTimeline() {
        ((TimelineActivity) getActivity()).showProgressBar();
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                clear();
                addAll(Tweet.fromJSONArray(json));
                Log.d("APICALL", toString());
                finishedRefreshing();
                ((TimelineActivity) getActivity()).hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("APICALL", errorResponse.toString());
            }
        });

    }
}
