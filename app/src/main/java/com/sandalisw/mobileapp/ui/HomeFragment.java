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

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.RecentSongAdapter;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.requests.responses.TopSongsResponse;
import com.sandalisw.mobileapp.viewmodels.SongViewModel;
import com.sandalisw.mobileapp.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class HomeFragment extends Fragment implements RecentSongAdapter.SongListener {

    private static final String TAG = "HomeFragment";

    private RecentSongAdapter songAdapter_recent;
    private RecentSongAdapter songAdapter_old;
    private RecentSongAdapter songAdapter_suggested;
    private RecentSongAdapter songAdapter_playlist;

    private RecyclerView recyclerView_recent;
    private RecyclerView recyclerView_old;
    private  RecyclerView recyclerView_suggested;
    private RecyclerView recyclerView_playlist;

    private IMainActivity mIMainActivity;
    private MediaMetadataCompat mSelectedMedia;
    private SongViewModel mSongViewModel;
    private UserViewModel mUserViewModel;
    private List<MediaMetadataCompat> mClassicLibrary = new ArrayList<>();
    private List<MediaMetadataCompat> mNewLibrary = new ArrayList<>();
    private List<MediaMetadataCompat> mSuggestedLibrary = new ArrayList<>();
    private List<MediaMetadataCompat> mPlaylistLibrary = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mSongViewModel = ViewModelProviders.of(getActivity()).get(SongViewModel.class);
        initRecyclerView(view);
        subscribeObservers();
    }

    private void initRecyclerView(View view){
        recyclerView_suggested = view.findViewById(R.id.new_songs_recyclerview);
        songAdapter_suggested = new RecentSongAdapter(getActivity(),this,3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(),HORIZONTAL,false);
        recyclerView_suggested.setLayoutManager(layoutManager3);
        recyclerView_suggested.setAdapter(songAdapter_suggested);

        recyclerView_playlist = view.findViewById(R.id.weekly_pl_recyclerview);
        songAdapter_playlist = new RecentSongAdapter(getActivity(),this,4);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(),HORIZONTAL,false);
        recyclerView_playlist.setLayoutManager(layoutManager4);
        recyclerView_playlist.setAdapter(songAdapter_playlist);


        recyclerView_recent = view.findViewById(R.id.recent_songs_recyclerview);
        songAdapter_recent = new RecentSongAdapter(getActivity(),this,1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
        recyclerView_recent.setLayoutManager(layoutManager1);
        recyclerView_recent.setAdapter(songAdapter_recent);

        recyclerView_old = view.findViewById(R.id.classical_songs_recyclerview);
        songAdapter_old = new RecentSongAdapter(getActivity(),this,2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
        recyclerView_old.setLayoutManager(layoutManager2);
        recyclerView_old.setAdapter(songAdapter_old);

    }

    private void subscribeObservers(){
        SharedPreferences sp = this.getActivity().getSharedPreferences("User_Data",MODE_PRIVATE);
        String cid = sp.getString("currentId","0");

        mSongViewModel.getSongs(cid).observe(this,new Observer<TopSongsResponse>(){

            @Override
            public void onChanged(@Nullable TopSongsResponse mData) {
                //this logic should be changed
                addtolibrary(mData.getRecent_songs(),1);
                addtolibrary(mData.getOld_songs(),2);
                addtolibrary(mData.getSuggested_songs(),3);
                addtolibrary(mData.getPlaylist_songs(),4);
                //
                songAdapter_recent.setDataList(mData.getRecent_songs());
                songAdapter_old.setDataList(mData.getOld_songs());
                songAdapter_suggested.setDataList(mData.getSuggested_songs());
                songAdapter_playlist.setDataList(mData.getPlaylist_songs());
            }
        });
    }

    public void addtolibrary(List<Song> sg, int category){
        if(category == 1){
            mNewLibrary.clear();
            for(Song song : sg){
                MediaMetadataCompat mData = new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.getId())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,song.getTitle())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,song.getArtist())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,song.getThumbnailUrl())
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getSong_url())
                        .build();
                mNewLibrary.add(mData);
            }
        }else if (category == 2){
            mClassicLibrary.clear();
            for(Song song : sg){
                MediaMetadataCompat mData = new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.getId())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,song.getTitle())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,song.getArtist())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,song.getThumbnailUrl())
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getSong_url())
                        .build();
                mClassicLibrary.add(mData);
            }
        }else if (category == 3){
            mSuggestedLibrary.clear();
            for(Song song : sg){
                MediaMetadataCompat mData = new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.getId())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,song.getTitle())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,song.getArtist())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,song.getThumbnailUrl())
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getSong_url())
                        .build();
                mSuggestedLibrary.add(mData);
            }
        }else{
            mPlaylistLibrary.clear();
            for(Song song : sg){
                MediaMetadataCompat mData = new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.getId())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,song.getTitle())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,song.getArtist())
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,song.getThumbnailUrl())
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getSong_url())
                        .build();
                mPlaylistLibrary.add(mData);
            }
        }



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }


    @Override
    public void onSongClick(int position, int category) {
        Log.d(TAG, "onSongClick: clicked"+category+"-" + position);
        if (category == 1) {
            mSelectedMedia = mNewLibrary.get(position);
            mIMainActivity.getMyApplication().setMediaItems(mNewLibrary);
            mSongViewModel.setPlaylist(mNewLibrary);

        }else if(category == 2) {
            mSelectedMedia = mClassicLibrary.get((position));
            mIMainActivity.getMyApplication().setMediaItems(mClassicLibrary);
            mSongViewModel.setPlaylist(mClassicLibrary);
        }else if(category == 3){
            mSelectedMedia = mSuggestedLibrary.get((position));
            mIMainActivity.getMyApplication().setMediaItems(mSuggestedLibrary);
            mSongViewModel.setPlaylist(mSuggestedLibrary);
        }else{
            mSelectedMedia = mPlaylistLibrary.get((position));
            mIMainActivity.getMyApplication().setMediaItems(mPlaylistLibrary);
            mSongViewModel.setPlaylist(mPlaylistLibrary);

        }

        SharedPreferences sp = this.getActivity().getSharedPreferences("User_Data",MODE_PRIVATE);
        String userId = sp.getString("userId","0");
        String cid = sp.getString("currentId","0");
        Log.d(TAG, "onSongClick: "+cid + userId);
        mUserViewModel.updateHistory(new Song(mSelectedMedia),userId);
        //adapter should highlight the selected song
        //songAdapter.setSelectedIndex(position);
        mIMainActivity.onMediaSelected(category,mSelectedMedia,position);
    }

}
