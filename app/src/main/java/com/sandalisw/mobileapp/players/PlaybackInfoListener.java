package com.sandalisw.mobileapp.players;

import android.support.v4.media.session.PlaybackStateCompat;

public interface PlaybackInfoListener {
    void updateUI(String newMediaId);

    void onPlaybackStateChanged(PlaybackStateCompat state);

    void onSeekTo(long progress, long max);

    void onPlaybackComplete();
}
