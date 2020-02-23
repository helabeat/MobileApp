package com.sandalisw.mobileapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.RecentSongAdapter;
import com.sandalisw.mobileapp.models.Song;
import com.sandalisw.mobileapp.room.AccessRoomData;
import com.sandalisw.mobileapp.utils.TemporaryPlaylist;
import com.sandalisw.mobileapp.viewmodels.SongViewModel;
import com.sandalisw.mobileapp.viewmodels.UserViewModel;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class ProfileFragment extends Fragment implements RecentSongAdapter.SongListener{

    private IMainActivity mIMainActivity;
    private View view;
    private UserViewModel mUserViewModel;
    private SongViewModel mSongViewModel;
    private RecyclerView recyclerView_myplaylist;
    private RecyclerView recyclerView_suggestions;
    private RecentSongAdapter playlist;
    private RecentSongAdapter suggestions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mSongViewModel = ViewModelProviders.of(getActivity()).get(SongViewModel.class);
        initRecyclerView(view);
        subscribeObservers();
    }

    private void initRecyclerView(View view) {
        recyclerView_myplaylist = view.findViewById(R.id.myplaylist_recyclerview);
        playlist = new RecentSongAdapter(getActivity(), this,-1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
        recyclerView_myplaylist.setLayoutManager(layoutManager);
        playlist.setDataList(new TemporaryPlaylist().getData());
        recyclerView_myplaylist.setAdapter(playlist);

        recyclerView_suggestions = view.findViewById(R.id.suggestions_recyclerview);
        suggestions = new RecentSongAdapter(getActivity(), this,-1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
        recyclerView_suggestions.setLayoutManager(layoutManager2);
        suggestions.setDataList(new TemporaryPlaylist().getdata());
        recyclerView_suggestions.setAdapter(suggestions);
    }

    private void subscribeObservers(){


    }


    @Override
    public void onSongClick(int position, int category) {

    }
}
