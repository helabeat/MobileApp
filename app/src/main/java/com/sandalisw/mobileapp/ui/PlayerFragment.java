package com.sandalisw.mobileapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.PlaylistItemAdapter;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.viewmodels.SongViewModel;
import com.sandalisw.mobileapp.viewmodels.UserViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class PlayerFragment extends Fragment implements PlaylistItemAdapter.PlaylistListener{

    private PlaylistItemAdapter playlistAdapter;
    private RecyclerView recyclerView;
    private List<MediaMetadataCompat> songList;

    private static final String TAG = "PlayerFragment";
    SongViewModel mSongViewModel;
    UserViewModel mUserViewModel;
    private View view;
    private IMainActivity mIMainActivity;
    private MediaMetadataCompat mSelectedMedia;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_player,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSongViewModel = ViewModelProviders.of(getActivity()).get(SongViewModel.class);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        initRecyclerView(view);
        subscribeObservers();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSongViewModel.getCurrentMedia().observe(this, new Observer<MediaMetadataCompat>() {
            @Override
            public void onChanged(@Nullable MediaMetadataCompat mdata) {
                setView(mdata);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
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

    private void initRecyclerView(View view){
        Log.d(TAG, "initRecyclerView: ");
        recyclerView = view.findViewById(R.id.playlist);
        playlistAdapter = new PlaylistItemAdapter(getActivity(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(playlistAdapter);
    }


    private void subscribeObservers(){
        Log.d(TAG, "subscribeObservers: ");
        mSongViewModel.getPlaylist().observe(this, new Observer<List<MediaMetadataCompat>>() {
            @Override
            public void onChanged(@Nullable List<MediaMetadataCompat> songs) {
                Log.d(TAG, "onChanged: ");
                playlistAdapter.setDataList(songs);
                songList = songs;

            }
        });
    }

    public void updateHistory(int position,String s){
        SharedPreferences sp = this.getActivity().getSharedPreferences("User_Data",MODE_PRIVATE);
        String userId = sp.getString("userId","0");


        mUserViewModel.updateHistory(new Song(songList.get(position)),userId);
    }

    @Override
    public void onSongClick(int position) {
        updateHistory(position,"");

        //mIMainActivity.getMyApplication().setMediaItems(songList);
        //adapter should highlight the selected song
        //songAdapter.setSelectedIndex(position);
        mSelectedMedia= songList.get(position);
        mIMainActivity.onMediaSelected(4, mSelectedMedia,position);

    }
}
