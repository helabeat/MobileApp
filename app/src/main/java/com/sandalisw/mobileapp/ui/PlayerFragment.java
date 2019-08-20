package com.sandalisw.mobileapp.ui;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.RecentSongAdapter;

public class PlayerFragment extends Fragment{

    private static final String TAG = "PlayerFragment";
    private IMainActivity mIMainActivity;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_player,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView songtitle = view.findViewById(R.id.current_song_playing);
        TextView artisttitle = (TextView)view.findViewById(R.id.current_artist_playing);
        ImageView thumbnail = (ImageView)view.findViewById(R.id.image_thumbnail);
        Log.d(TAG, "onViewCreated: getPlayer");

        MediaMetadataCompat mdata = mIMainActivity.getMediaData();
        Log.d(TAG, "onViewCreated: "+mdata.getDescription().getTitle());
        songtitle.setText(mdata.getDescription().getTitle());
        artisttitle.setText(mdata.getDescription().getSubtitle());
        Glide.with(getActivity())
                .load(mdata.getDescription().getIconUri())
                .error(R.drawable.ic_launcher_background)
                .into((thumbnail));

    }

}
