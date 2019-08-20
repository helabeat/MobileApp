package com.sandalisw.mobileapp.ui;


import android.support.v4.media.MediaMetadataCompat;


import com.sandalisw.mobileapp.MediaApplication;

public interface IMainActivity {

    void playPause();

    MediaApplication getMyApplication();

    void onMediaSelected(MediaMetadataCompat mediaItem);

    void setMediadata(MediaMetadataCompat mData);

    MediaMetadataCompat getMediaData();
}
