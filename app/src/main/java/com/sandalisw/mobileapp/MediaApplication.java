package com.sandalisw.mobileapp;

import android.app.Application;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MediaApplication  extends Application {

    private static final String TAG = "MediaApplication";

    private static MediaApplication mInstance;
    private List<MediaBrowserCompat.MediaItem> mMediaItems = new ArrayList<>();
    private TreeMap<String, MediaMetadataCompat> mTreeMap = new TreeMap<>();


    public static MediaApplication getInstance(){
        Log.d(TAG, "getInstance: ");
        if(mInstance == null){
            Log.d(TAG, "getInstance: Null");
            mInstance = new MediaApplication();
        }
        return mInstance;
    }

    public List<MediaBrowserCompat.MediaItem> getMediaItems(){
        Log.d(TAG, "getMediaItems: "+mMediaItems.size());
        return mMediaItems;
    }

    public TreeMap<String, MediaMetadataCompat> getTreeMap(){
        return mTreeMap;
    }

    public void setMediaItems(List<MediaMetadataCompat> mediaItems){
        mMediaItems.clear();
        for(MediaMetadataCompat item: mediaItems){
            mMediaItems.add(
                    new MediaBrowserCompat.MediaItem(
                            item.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
            );
            mTreeMap.put(item.getDescription().getMediaId(), item);
            Log.d(TAG, "setMediaItems: called: adding media item: " + mTreeMap.get(item.getDescription().getMediaId()));
        }
    }

    public MediaMetadataCompat getMediaItem(String mediaId){
        Log.d(TAG, "getMediaItem: called");
        return mTreeMap.get(mediaId);
    }
}
