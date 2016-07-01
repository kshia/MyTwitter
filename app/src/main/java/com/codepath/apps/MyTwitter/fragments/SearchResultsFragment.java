package com.codepath.apps.MyTwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.MyTwitter.TwitterApplication;
import com.codepath.apps.MyTwitter.TwitterClient;
import com.codepath.apps.MyTwitter.activities.ProfileActivity;
import com.codepath.apps.MyTwitter.activities.SearchActivity;
import com.codepath.apps.MyTwitter.activities.TimelineActivity;
import com.codepath.apps.MyTwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

import cz.msebera.android.httpclient.Header;

/**
 * Created by kshia on 6/30/16.
 */
public class SearchResultsFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    public static SearchResultsFragment newInstance(String query) {
        SearchResultsFragment searchFragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        searchFragment.setArguments(args);
        return searchFragment;
    }

    public void populateTimeline() {
        String query = getArguments().getString("query");

        client.search(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                clear();
                try {
                    addAll(Tweet.fromJSONArray(json.getJSONArray("statuses")));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("APICALL", toString());
                finishedRefreshing();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("APICALL", errorResponse.toString());
            }
        });
    }
}
