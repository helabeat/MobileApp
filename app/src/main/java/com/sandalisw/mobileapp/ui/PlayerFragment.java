package com.sandalisw.mobileapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.google.api.LogDescriptor;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.RecentSongAdapter;
import com.sandalisw.mobileapp.viewmodels.SongViewModel;
import com.sandalisw.mobileapp.viewmodels.UserViewModel;

public class PlayerFragment extends Fragment{

    private static final String TAG = "PlayerFragment";
    SongViewModel mSongViewModel;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_player,container,false);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {
            Log.d("MyFragment", "Fragment is visible.");
            mSongViewModel.getCurrentMedia().observe(this, new Observer<MediaMetadataCompat>() {
                @Override
                public void onChanged(@Nullable MediaMetadataCompat mediaMetadataCompat) {
                    Log.d(TAG, "onChanged: media changed");
                    setView(mediaMetadataCompat);
                }
            });

        }else {
            Log.d("MyFragment", "Fragment is not visible.");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setView(MediaMetadataCompat mdata) {
        Log.d(TAG, "setView: ");
        TextView songtitle = view.findViewById(R.id.current_song_playing);
        TextView artisttitle = (TextView)view.findViewById(R.id.current_artist_playing);
        ImageView thumbnail = (ImageView)view.findViewById(R.id.image_thumbnail);

        songtitle.setText(mdata.getDescription().getTitle());
        artisttitle.setText(mdata.getDescription().getSubtitle());
        Glide.with(getActivity())
                .load(mdata.getDescription().getIconUri())
                .error(R.drawable.ic_launcher_background)
                .into((thumbnail));

    }

}
