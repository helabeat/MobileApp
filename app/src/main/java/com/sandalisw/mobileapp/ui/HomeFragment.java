package com.sandalisw.mobileapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.RecentSongAdapter;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.viewmodels.SongViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class HomeFragment extends Fragment implements RecentSongAdapter.SongListener {

    private static final String TAG = "HomeFragment";

    private RecentSongAdapter songAdapter;
    private RecyclerView recyclerView;
    //private Library mLibrary = new Library();
    private IMainActivity mIMainActivity;
    private MediaMetadataCompat mSelectedMedia;
    private SongViewModel mViewModel;
    private List<MediaMetadataCompat> mLibrary = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SongViewModel.class);

        initRecyclerView(view);
        subscribeObservers();
    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recent_songs_recyclerview);
        songAdapter = new RecentSongAdapter(getActivity(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(songAdapter);
    }

    private void subscribeObservers(){
        mViewModel.getSongs().observe(this,new Observer<List<Song>>(){

            @Override
            public void onChanged(@Nullable List<Song> mData) {
                //this logic should be changed
                addtolibrary(mData);
                songAdapter.setDataList(mData);
            }
        });
    }

    public void addtolibrary(List<Song> mediaData){
        for(Song song:mediaData){
            MediaMetadataCompat mData = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.getId())
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,song.getTitle())
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,song.getThumbnailUrl())
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getSong_url())
                    .build();

            mLibrary.add(mData);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }


    @Override
    public void onSongClick(int position) {
        Log.d(TAG, "onMediaSelected: "+mLibrary.size());
        mIMainActivity.getMyApplication().setMediaItems(mLibrary);
        mSelectedMedia = mLibrary.get(position);
        //adapter should highlight the selected song
        //songAdapter.setSelectedIndex(position);
        mIMainActivity.onMediaSelected(mSelectedMedia);
    }

}
