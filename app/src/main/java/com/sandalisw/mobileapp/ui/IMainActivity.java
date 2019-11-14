package com.sandalisw.mobileapp.ui;


import android.support.v4.media.MediaMetadataCompat;


import com.sandalisw.mobileapp.MediaApplication;

public interface IMainActivity {

    void playPause();

    void skipNext ();

    MediaApplication getMyApplication();

    void onMediaSelected(Integer playlistId,MediaMetadataCompat mediaItem);

    void setMediadata(MediaMetadataCompat mData);

}
