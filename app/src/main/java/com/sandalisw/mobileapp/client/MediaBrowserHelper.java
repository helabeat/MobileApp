package com.sandalisw.mobileapp.client;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import java.util.List;

public class MediaBrowserHelper {

    private static final String TAG = "MediaBrowserHelper";

    private final Context mContext;
    private final  Class<? extends MediaBrowserServiceCompat> mMediaBrowserServiceClass;

    private MediaBrowserCompat mMediaBrowser;
    private MediaControllerCompat mMediaController;
    private MediaBrowserConnectionCallback mConnectionCallback;
    private MediaBrowserSubscriptionCallback mSubscriptionCallback;
    private MediaControllerCallback mMediaControllerCallback;
    private MediaBrowserHelperCallback mMediaBrowserHelperCallback;

    public MediaBrowserHelper(Context context, Class<? extends MediaBrowserServiceCompat> mediaBrowserServiceClass) {
        this.mContext = context;
        this.mMediaBrowserServiceClass = mediaBrowserServiceClass;

        mConnectionCallback = new MediaBrowserConnectionCallback();
        mSubscriptionCallback = new MediaBrowserSubscriptionCallback();
        mMediaControllerCallback = new MediaControllerCallback();
    }

    public void setMediaBrowserHelperCallback(MediaBrowserHelperCallback callback){
        mMediaBrowserHelperCallback = callback;
    }

    public void subscribeToPlaylist(Integer playlistId){
        mMediaBrowser.subscribe(playlistId.toString(), mSubscriptionCallback);
    }

    public void onStart(){
        if(mMediaBrowser == null){
            mMediaBrowser = new MediaBrowserCompat(mContext,
                    new ComponentName(mContext,mMediaBrowserServiceClass),
                    mConnectionCallback,
                    null
            );
            mMediaBrowser.connect();
        }
        Log.d(TAG, "onStart: CONNECTING to service");
    }

    public void onStop(){
        Log.d(TAG, "onStop: called");
        if(mMediaController != null){
            mMediaController.unregisterCallback(mMediaControllerCallback);
            mMediaController = null;
        }

        if(mMediaBrowser != null && mMediaBrowser.isConnected()){
            mMediaBrowser.disconnect();
            mMediaBrowser = null;
        }

        Log.d(TAG, "onStop: DISCONNECTING from the servie");
    }


    private  class MediaControllerCallback extends MediaControllerCompat.Callback{
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            if(mMediaBrowserHelperCallback != null){
                mMediaBrowserHelperCallback.onPlaybackStateChanged(state);
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            if(mMediaBrowserHelperCallback != null){
                mMediaBrowserHelperCallback.onMetaDataChanged(metadata);
            }
        }
    }


    private class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback{

        @Override
        public void onConnected() {
            Log.d(TAG, "onConnected: called");

            try{
                mMediaController = new MediaControllerCompat(mContext, mMediaBrowser.getSessionToken());
                mMediaController.registerCallback(mMediaControllerCallback);
                mMediaBrowserHelperCallback.onMediaControllerConnected(mMediaController);
            }catch(RemoteException ex){
                Log.d(TAG, "onConnected: connection problem"+ ex.toString());
            }

            mMediaBrowser.subscribe(mMediaBrowser.getRoot(), mSubscriptionCallback); //subscribe to a playlist should be used when we play from cache.
        }
    }

    public class MediaBrowserSubscriptionCallback extends MediaBrowserCompat.SubscriptionCallback{
        @Override
        public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
            Log.d(TAG, "onChildrenLoaded: CALLED " + parentId+","+children.toString());

            for(final MediaBrowserCompat.MediaItem mediaItem : children){
                mMediaController.addQueueItem(mediaItem.getDescription());
            }
        }
    }

    public MediaControllerCompat.TransportControls getTransportControls(){
        Log.d(TAG, "getTransportControls: ");
        if(mMediaController == null){
            throw new IllegalStateException("MediaController is null!");
        }
        return mMediaController.getTransportControls();
    }
}
