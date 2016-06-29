package com.codepath.apps.MyTwitter.fragments;

/**
 * Created by kshia on 6/28/16.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.MyTwitter.R;
import com.codepath.apps.MyTwitter.TwitterClient;
// ...

public class ComposeFragment extends DialogFragment {

    private EditText mEditText;
    private Button btnCompose;

    public ComposeFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ComposeFragment newInstance(String title) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etTweet);
        mEditText.setHint("What's Happening?");
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Default Title");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnCompose  = (Button) view.findViewById(R.id.btnCompose);
        btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostTweetListener listener = (PostTweetListener) getActivity();
                listener.onPostTweet(mEditText.getText().toString());
                dismiss();
            }
        });
    }

    public interface PostTweetListener {
        void onPostTweet(String tweetText);
    }


}
