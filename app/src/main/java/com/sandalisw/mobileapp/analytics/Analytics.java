package com.sandalisw.mobileapp.analytics;

import android.support.v4.media.MediaMetadataCompat;

public class Analytics {

    public static void analytics(MediaMetadataCompat mediaItem, String UserID){

        ArtistAnalytics.artistAnalytics(mediaItem);
        SongAnalytics.songAnalytics(mediaItem);
        UserAnalytics.userHistry(mediaItem, UserID);
        UserArtistPreference.userArtistPreferences(mediaItem);
        UserSongPreferences.userSongPreferences(mediaItem,UserID);
    }
}
