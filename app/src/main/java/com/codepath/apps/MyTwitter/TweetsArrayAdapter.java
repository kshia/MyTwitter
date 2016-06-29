package com.codepath.apps.MyTwitter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.MyTwitter.activities.ProfileActivity;
import com.codepath.apps.MyTwitter.models.Tweet;
import com.codepath.apps.MyTwitter.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by kshia on 6/27/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);

        // TODO: Use ViewHolder pattern

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        tvUserName.setText(tweet.getUser().getName() + " @" + tweet.getUser().getScreenName() + " " + tweet.getRelTimeStamp());
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", Parcels.wrap(tweet.getUser()));
                i.putExtra("screen_name", tweet.getUser().getScreenName());     // Allow for Async loading of timeline
                getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
