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

    private RecyclerView recyclerView_recent;
    private RecyclerView recyclerView_old;

    private IMainActivity mIMainActivity;
    private MediaMetadataCompat mSelectedMedia;
    private SongViewModel mSongViewModel;
    private UserViewModel mUserViewModel;
    private List<MediaMetadataCompat> mLibrary = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        initRecyclerView(view);
        subscribeObservers();
    }

    private void initRecyclerView(View view){
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
        mSongViewModel.getSongs().observe(this,new Observer<TopSongsResponse>(){

            @Override
            public void onChanged(@Nullable TopSongsResponse mData) {
                //this logic should be changed
                addtolibrary(mData.getRecent_songs());
                addtolibrary(mData.getOld_songs());
                //
                songAdapter_recent.setDataList(mData.getRecent_songs());
                songAdapter_old.setDataList(mData.getOld_songs());
            }
        });
    }

    public void addtolibrary(List<Song> sg){
        Log.d(TAG, "addtolibrary: "+sg.size());
        for(Song song : sg){
            MediaMetadataCompat mData = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.getId())
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,song.getTitle())
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,song.getArtist())
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
    public void onSongClick(int position, int category) {
        Log.d(TAG, "onSongClick: clicked");
        if (category == 1) {
            mSelectedMedia = mLibrary.get(position);
        }else{
            mSelectedMedia = mLibrary.get((position+10));
        }
        mIMainActivity.getMyApplication().setMediaItems(mLibrary);

        SharedPreferences sp = this.getActivity().getSharedPreferences("User_Data",MODE_PRIVATE);
        String userId = sp.getString("userId","0");
        mUserViewModel.updateHistory(new Song(mSelectedMedia),userId);



        //adapter should highlight the selected song
        //songAdapter.setSelectedIndex(position);
        mIMainActivity.onMediaSelected(mSelectedMedia);
    }

}
