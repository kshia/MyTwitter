package com.codepath.apps.MyTwitter.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.MyTwitter.R;
import com.codepath.apps.MyTwitter.TwitterApplication;
import com.codepath.apps.MyTwitter.TwitterClient;
import com.codepath.apps.MyTwitter.fragments.SearchResultsFragment;
import com.codepath.apps.MyTwitter.fragments.UserTimelineFragment;

public class SearchActivity extends AppCompatActivity {

    SearchResultsFragment searchResultsFragment;
    TwitterClient client;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        query = getIntent().getStringExtra("query");

        client = TwitterApplication.getRestClient();

        if (savedInstanceState == null) {
            searchResultsFragment = SearchResultsFragment.newInstance(query);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, searchResultsFragment);
            ft.commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(query);
    }

}
