package com.sandalisw.mobileapp.ui;

import android.app.Activity;
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
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.SearchItemAdapter;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.viewmodels.SongViewModel;
import com.sandalisw.mobileapp.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class SearchFragment extends Fragment implements SearchItemAdapter.SongResultSelection {


    private static final String TAG = "SearchFragment";
    private View view;
    private SongViewModel mSongViewModel;
    private UserViewModel mUserViewModel;
    private SearchItemAdapter searchItemAdapter;
    private RecyclerView recyclerView;
    private List<Song> dataList;
    private IMainActivity mIMainActivity;
    private MediaMetadataCompat mSelectedMedia;
    //private SearchView searchview;
    private List<MediaMetadataCompat> mLibrary = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mSongViewModel = ViewModelProviders.of(getActivity()).get(SongViewModel.class);

        initSearchView();
        initRecyclerView(view);

    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.search_results_recyclerview);
        searchItemAdapter = new SearchItemAdapter(getActivity(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchItemAdapter);
    }


    private void subscribeObservers(final String s){
        mSongViewModel.searchSong(s).observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                searchItemAdapter.setDataList(songs);
                dataList = songs;
                Log.d(TAG, "onChanged: no search "+songs.get(0).getId());
                if(songs.get(0).getId() == "-1"){
                    Log.d(TAG, "onChanged: no search");
                    updateHistory(-1,s);
                }
            }
        });
    }
    private void initSearchView(){
        final SearchView searchview = view.findViewById(R.id.search_view);

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: "+s);
                subscribeObservers(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d(TAG, "onQueryTextChange: "+s);
                subscribeObservers(s);
                return false;
            }
        });
    }

    private void addToMediaLibrary(List<Song> songs){
        mLibrary.clear();
        Log.d(TAG, "addToMediaLibrary: "+mLibrary.size());
        for(Song song : songs) {
            MediaMetadataCompat mData = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.getId())
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, song.getTitle())
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, song.getArtist())
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, song.getThumbnailUrl())
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

    public void updateHistory(int position,String s){
        SharedPreferences sp = this.getActivity().getSharedPreferences("User_Data",MODE_PRIVATE);
        String userId = sp.getString("userId","0");

        if(position == -1){
            mUserViewModel.updateHistory(new Song(s, -1),userId);
        }

        mUserViewModel.updateHistory(new Song(dataList.get(position)),userId);
    }

    @Override
    public void onSongClick(int position) {
        updateHistory(position,"");

        addToMediaLibrary(dataList);
        mIMainActivity.getMyApplication().setMediaItems(mLibrary);
        //adapter should highlight the selected song
        //songAdapter.setSelectedIndex(position);
        Log.d(TAG, "onSongClick: search"+position);
        mSelectedMedia= mLibrary.get(position);
        mIMainActivity.onMediaSelected(3, mSelectedMedia,position);

        mSongViewModel.setPlaylist(mLibrary);


        //Clear SearchView
        //searchview.setIconified(true);
        hideKeyboardFrom(getContext(),view);


    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
