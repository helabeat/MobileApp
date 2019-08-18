package com.sandalisw.mobileapp.ui;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sandalisw.mobileapp.R;

public class PlayerFragment extends Fragment {
    private IMainActivity mIMainActivity;
    private View view;
    private Context mContext;

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
        MediaMetadataCompat mdata = mIMainActivity.getMediadata();

        TextView songtitle = (TextView)view.findViewById(R.id.current_song_playing);
        songtitle.setText(mdata.getDescription().getTitle());
        TextView artisttitle = (TextView)view.findViewById(R.id.current_artist_playing);
        artisttitle.setText("artist x");
        ImageView thumbnail = (ImageView)view.findViewById(R.id.image_thumbnail);
        Glide.with(getActivity())
                .load(mdata.getDescription().getIconUri())
                .error(R.drawable.ic_launcher_background)
                .into(thumbnail);
    }
}
