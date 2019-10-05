package com.sandalisw.mobileapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import com.sandalisw.mobileapp.MediaApplication;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.adapters.TabAdapter;
import com.sandalisw.mobileapp.client.MediaBrowserHelper;
import com.sandalisw.mobileapp.client.MediaBrowserHelperCallback;
import com.sandalisw.mobileapp.services.MediaService;


public class MainActivity extends AppCompatActivity implements IMainActivity, MediaBrowserHelperCallback {

    private static final String TAG = "MainActivity";

    private SeekbarBroadcastReceiver mSeekbarBroadcast;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    private ViewPager viewPager;
    private boolean mIsPlaying;

    private MediaBrowserHelper mMediaBrowserHelper;
    private MediaApplication mMediaApplication;
    private MediaMetadataCompat mediaMetadata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //onetime signup check
        SharedPreferences sp_user = getSharedPreferences("User_Data",MODE_PRIVATE);
        boolean b = sp_user.getBoolean("loggedIn",false);
        if(!b){
            Intent i = new Intent(this,ConsentActivity.class);
            startActivity(i);
            finish();
        }

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());

        tabAdapter.addFragment(new HomeFragment(), "Home");
        tabAdapter.addFragment(new SearchFragment(), "Search");
        tabAdapter.addFragment(new PlayerFragment(), "Player");
        tabAdapter.addFragment(new ProfileFragment(), "Profile");


        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcons();

        if (findViewById(R.id.media_player_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            MediaControllerFragment mediaControllerFragment = new MediaControllerFragment();

            mediaControllerFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.media_player_container, mediaControllerFragment).commit();
        }

        mMediaApplication = MediaApplication.getInstance();

        mMediaBrowserHelper = new MediaBrowserHelper(this, MediaService.class);
        mMediaBrowserHelper.setMediaBrowserHelperCallback(this);

    }

    private void setUpTabIcons(){
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_search);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_player);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_profile);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaBrowserHelper.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaBrowserHelper.onStop();
    }

    @Override
    public void playPause() {
        if(mIsPlaying){
            mMediaBrowserHelper.getTransportControls().pause();
        }else{
            mMediaBrowserHelper.getTransportControls().play();
        }
    }

    @Override
    public MediaApplication getMyApplication() {
        return  mMediaApplication;
    }


    @Override
    public void onMediaSelected(MediaMetadataCompat mediaItem) {
        if(findViewById(R.id.seek_bar).getVisibility() != ViewPager.VISIBLE){
            findViewById(R.id.seek_bar).setVisibility(View.VISIBLE);
        }
        if(mediaItem != null){
            setMediadata(mediaItem);
            //mMediaBrowserHelper.subscribeToPlaylist(playlistId);
            mMediaBrowserHelper.getTransportControls().playFromMediaId(mediaItem.getDescription().getMediaId(),null);

        }else{
            Toast.makeText(this,"Select Something to play",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setMediadata(MediaMetadataCompat mData) {
        Log.d(TAG, "setMediadata: "+mData.getDescription().getTitle());
        mediaMetadata = mData;
    }

    @Override
    public void onMetaDataChanged(MediaMetadataCompat metaData) {
        //New metadata for song
        if(metaData == null){
            return;
        }
        getMediaControllerFragment().setMediaData(metaData);
    }

    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {
        mIsPlaying = state != null &&
                state.getState() == PlaybackStateCompat.STATE_PLAYING;

        //update UI
        if(getMediaControllerFragment() != null){
            getMediaControllerFragment().setIsPlaying(mIsPlaying);
        }
        if(state.getState() == PlaybackStateCompat.STATE_STOPPED){
            getMediaControllerFragment().setIsPlaying(mIsPlaying);
        }

    }

    @Override
    public void onMediaControllerConnected(MediaControllerCompat mediaController) {
        getMediaControllerFragment().getMediaSeekbar().setMediaController(mediaController);
    }

    private MediaControllerFragment getMediaControllerFragment(){
        MediaControllerFragment mediaControllerFragment = (MediaControllerFragment)getSupportFragmentManager()
                .findFragmentById(R.id.media_player_container);
        if(mediaControllerFragment != null){
            return mediaControllerFragment;
        }
        return  null;
    }


    private void initSeekBarBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.broadcast_seekbar_update));
        mSeekbarBroadcast = new SeekbarBroadcastReceiver();
        registerReceiver(mSeekbarBroadcast, intentFilter);
    }

    private class  SeekbarBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long seekProgress = intent.getLongExtra("SEEK_BAR_PROGRESS",0);
            long maxProgress = intent.getLongExtra("SEEK_BAR_MAX",0);
            if(!getMediaControllerFragment().getMediaSeekbar().isTracking()){
                getMediaControllerFragment().getMediaSeekbar().setProgress((int)seekProgress);
                getMediaControllerFragment().getMediaSeekbar().setMax((int)maxProgress);
            }

        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: called");
        super.onResume();
        initSeekBarBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mSeekbarBroadcast != null){
            unregisterReceiver(mSeekbarBroadcast);
        }
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
