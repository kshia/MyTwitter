package com.codepath.apps.MyTwitter.activities;

import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.MyTwitter.R;
import com.codepath.apps.MyTwitter.TwitterApplication;
import com.codepath.apps.MyTwitter.TwitterClient;
import com.codepath.apps.MyTwitter.fragments.ComposeFragment;
import com.codepath.apps.MyTwitter.fragments.UserTimelineFragment;
import com.codepath.apps.MyTwitter.models.Tweet;
import com.codepath.apps.MyTwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity implements ComposeFragment.PostTweetListener{

    TwitterClient client;
    MenuItem miActionProgressItem;
    User user;
    UserTimelineFragment userTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Parcelable userParcel = getIntent().getParcelableExtra("user");

        client = TwitterApplication.getRestClient();


        String screenName = getIntent().getStringExtra("screen_name");
        getSupportActionBar().setTitle("@" + screenName);

        if (userParcel == null) {
            showProgressBar();
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                    hideProgressBar();
                }
            });
        }
        else {
            user = Parcels.unwrap(userParcel);
            populateProfileHeader(user);
        }


        if (savedInstanceState == null) {
            userTimelineFragment = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFragment);
            ft.commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComposeFragment();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        return super.onCreateOptionsMenu(menu);

    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagLine());
        tvFollowers.setText("" + user.getFollowerCount());
        tvFollowing.setText("" + user.getFollowingCount());

        Picasso.with(this).load(user.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(20, 20))
                .into(ivProfileImage);
    }

    public void showProgressBar() {
        // Show progress item
        if (miActionProgressItem != null) {
            miActionProgressItem.setVisible(true);
        }
    }

    public void hideProgressBar() {
        // Hide progress item
        if (miActionProgressItem != null) {
            miActionProgressItem.setVisible(false);
        }
    }

    @Override
    public void onPostTweet(String tweetText) {
        showProgressBar();
        Toast.makeText(ProfileActivity.this, "Tweet: " + tweetText, Toast.LENGTH_SHORT).show();
        client.postTweet(tweetText, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                userTimelineFragment.addOne(Tweet.fromJSON(response));
            }
        });
        hideProgressBar();

    }

    public void onCompose(MenuItem item) {
        openComposeFragment();
    }

    public void openComposeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment composeFragment = ComposeFragment.newInstance("Post a Tweet");
        composeFragment.show(fm, "fragment_edit_name");
    }
}
