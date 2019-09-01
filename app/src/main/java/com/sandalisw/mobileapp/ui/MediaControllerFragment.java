package com.sandalisw.mobileapp.ui;

import android.content.Context;
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
import com.sandalisw.mobileapp.MediaSeekbar;
import com.sandalisw.mobileapp.R;

public class MediaControllerFragment extends Fragment implements View.OnClickListener {

    private MediaSeekbar mSeekbar;
    private TextView msongTitile;
    private ImageView mThumbnail;
    private ImageView mPlayPause;

    private IMainActivity mIMainActivity;

    private static final String TAG = "MediaControllerFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: saved");

        mSeekbar = view.findViewById(R.id.seek_bar);
        msongTitile = view.findViewById(R.id.current_song);
        mThumbnail = view.findViewById(R.id.thumbnail);
        mPlayPause = view.findViewById(R.id.playPause);

        mPlayPause.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.playPause){
            mIMainActivity.playPause();
        }
    }

    public MediaSeekbar getMediaSeekbar(){
        return mSeekbar;
    }

    public void setIsPlaying(boolean isPlaying){
        if(isPlaying){
            Log.d(TAG, "setIsPlaying: true");
            Glide.with(getActivity())
                    .load(R.drawable.ic_pause)
                    .into(mPlayPause);

        }else{
            Log.d(TAG, "setIsPlaying: false");
            Glide.with(getActivity())
                    .load(R.drawable.ic_play)
                    .into(mPlayPause);
        }
    }

    public void setMediaData(MediaMetadataCompat mediaItem){
        Log.d(TAG, "setMediaData: called for media title and thumbnail");
        msongTitile.setText(mediaItem.getDescription().getTitle());
        Glide.with(getActivity())
                .load(mediaItem.getDescription().getIconUri())
                .error(R.drawable.ic_launcher_background)
                .into(mThumbnail);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

}
