package com.codepath.apps.MyTwitter.activities;

import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import org.parceler.Parcels;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tweet");


        client = TwitterApplication.getRestClient();


        user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        if(tweet == null) {
            Log.d("DetailActivity", "tweet is null");
        }

        populate(user, tweet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    private void populate(User user, Tweet tweet) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvRetweetCount = (TextView) findViewById(R.id.tvRetweetCount);
        TextView tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        TextView tvLikesCount = (TextView) findViewById(R.id.tvLikesCount);
        TextView tvTweetBody = (TextView) findViewById(R.id.tvTweetBody);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ImageView ivMedia = (ImageView) findViewById(R.id.ivMedia);

        //Toast.makeText(DetailActivity.this, user.getScreenName(), Toast.LENGTH_SHORT).show();

        tvTweetBody.setText(tweet.getBody());
        tvTimeStamp.setText(tweet.getTimeStamp());
        tvName.setText(user.getName());
        tvScreenName.setText("@" + user.getScreenName());
        tvRetweetCount.setText(tweet.getRetweetCount() + " ");
        tvLikesCount.setText(tweet.getLikeCount() + " ");

        Picasso.with(this).load(user.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(20, 20))
                .into(ivProfileImage);

        ivMedia.setImageResource(0);
        Picasso.with(this).cancelRequest(ivMedia);

        if (tweet.getMediaUrl() != null) {
            Picasso.with(this).load(tweet.getMediaUrl()).into(ivMedia);
        }
    }

}