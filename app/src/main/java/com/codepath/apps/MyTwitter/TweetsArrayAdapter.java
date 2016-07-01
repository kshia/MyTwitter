package com.codepath.apps.MyTwitter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.MyTwitter.activities.DetailActivity;
import com.codepath.apps.MyTwitter.activities.ProfileActivity;
import com.codepath.apps.MyTwitter.models.Tweet;
import com.codepath.apps.MyTwitter.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.Collection;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kshia on 6/27/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);

        // TODO: Use ViewHolder pattern

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        ImageView ivMedia = (ImageView) convertView.findViewById(R.id.ivMedia);
        TextView tvRelTime = (TextView) convertView.findViewById(R.id.tvRelTime);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);

        tvUserName.setText(tweet.getUser().getName());
        tvScreenName.setText(" @" + tweet.getUser().getScreenName());
        tvRelTime.setText(tweet.getRelTimeStamp());
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(20, 20))
                .into(ivProfileImage);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), ProfileActivity.class);
                    i.putExtra("user", Parcels.wrap(tweet.getUser()));
                    i.putExtra("screen_name", tweet.getUser().getScreenName());     // Allow for Async loading of timeline
                    getContext().startActivity(i);
                }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "display activity" + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("user", Parcels.wrap(tweet.getUser()));
                i.putExtra("tweet", Parcels.wrap(tweet));
                getContext().startActivity(i);
            }
        });

        ivMedia.setImageResource(0);
        Picasso.with(getContext()).cancelRequest(ivMedia);

        if (tweet.getMediaUrl() != null) {
            Picasso.with(getContext()).load(tweet.getMediaUrl()).into(ivMedia);
        }

        return convertView;
    }

}
