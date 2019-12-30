package com.sandalisw.mobileapp.services;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.sandalisw.mobileapp.MediaApplication;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.players.MediaPlayerAdapter;
import com.sandalisw.mobileapp.players.PlaybackInfoListener;
import com.sandalisw.mobileapp.services.notifications.MediaNotificationManager;

import java.util.ArrayList;
import java.util.List;

import static com.sandalisw.mobileapp.utils.Constants.MEDIA_QUEUE_POSITION;

public class MediaService extends MediaBrowserServiceCompat {

    private static final String TAG = "MediaService";

    private MediaSessionCompat mSession;//central host for playing media files
    private MediaPlayerAdapter mPlayback;
    private MediaApplication mAppication;
    private MediaNotificationManager mMediaNotificationManager;
    private boolean mIsServiceRunning;


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: MEDIA SERVICE");
        super.onCreate();
        mAppication = MediaApplication.getInstance();

        //Build the MediaSession
        mSession = new MediaSessionCompat(this, TAG);

        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                // https://developer.android.com/guide/topics/media-apps/mediabuttons#mediabuttons-and-active-mediasessions
                // Media buttons on the device
                // (handles the PendingIntents for MediaButtonReceiver.buildMediaButtonPendingIntent)
                MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS); // Control the items in the queue (aka playlist)
        // See https://developer.android.com/guide/topics/media-apps/mediabuttons for more info on flags

        mSession.setCallback(new MediaSessionCallback());

        // A token that can be used to create a MediaController for this session
        setSessionToken(mSession.getSessionToken());

        mPlayback = new MediaPlayerAdapter(this,new MediaPlayerListener());

        mMediaNotificationManager = new MediaNotificationManager(this);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        mPlayback.stop();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSession.release();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String s, int i, @Nullable Bundle bundle) {

        if(s.equals(getApplicationContext().getPackageName())){
            //allow to browse media
            return  new BrowserRoot("real_playlist",null);
        }
        return new BrowserRoot("empty_media",null);//when somebody shouldn't be browsing
    }

    @Override
    public void onLoadChildren(@NonNull String s, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

        if(TextUtils.equals("empty_media",s)){
          result.sendResult(null);
          return;
        }

        result.sendResult(mAppication.getMediaItems());//pass the mediaItems
    }

    public class  MediaSessionCallback extends MediaSessionCompat.Callback{

        private final List<MediaSessionCompat.QueueItem> mPlaylist = new ArrayList<>();
        private int mQueueIndex = -1;
        private MediaMetadataCompat mPreparedMedia;

        @Override
        public void onPrepare() {
            if(mQueueIndex <0 && mPlaylist.isEmpty()){
                return;
            }

            String mediaId = mPlaylist.get(mQueueIndex).getDescription().getMediaId();
            mPreparedMedia = mAppication.getMediaItem(mediaId);
            mSession.setMetadata(mPreparedMedia);

            if(!mSession.isActive()){
                mSession.setActive(true);
            }

        }

        @Override
        public void onPlay() {
//            if(!isReadyToPlay()){
//                return;
//            }

            if(mPreparedMedia == null){
                onPrepare();
            }
            mPlayback.playFromMedia(mPreparedMedia);

        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {

            mPreparedMedia = mAppication.getMediaItem(mediaId);
            mSession.setMetadata(mPreparedMedia);
            if(!mSession.isActive()){
                mSession.setActive(true);
            }
            mPlayback.playFromMedia(mPreparedMedia);

            int newQueuePosition = extras.getInt(MEDIA_QUEUE_POSITION,-1);
            if(newQueuePosition == -1){
                mQueueIndex++;
            }else{
                mQueueIndex = newQueuePosition;
            }
        }

        @Override
        public void onPause() {
            mPlayback.pause();
        }

        @Override
        public void onStop() {
            mPlayback.stop();
            mSession.setActive(false);
        }

        @Override
        public void onSkipToNext() {
            Log.d(TAG, "onSkipToNext: SKIPPING TO NEXT"+mPlaylist.size());
            mQueueIndex = (++mQueueIndex % mPlaylist.size()); //check whether the queue index exceeds the size of the playlist
            mPreparedMedia = null;
            onPlay();

        }

        @Override
        public void onSkipToPrevious() {
            Log.d(TAG, "onSkipToPrevious: SKIPPING TO PREVIOUS");
            mQueueIndex = mQueueIndex > 0 ? mQueueIndex-1 : mPlaylist.size()-1 ;
            mPreparedMedia = null;
            onPlay();
        }

        @Override
        public void onSeekTo(long pos) {
            mPlayback.seekTo(pos);
        }

        @Override
        public void onAddQueueItem(MediaDescriptionCompat description) {
            Log.d(TAG, "onAddQueueItem: position in list" + mPlaylist.size());
            mPlaylist.add(new MediaSessionCompat.QueueItem(description,description.hashCode()));
            mQueueIndex = (mQueueIndex == -1) ? 0 : mQueueIndex;

            mSession.setQueue(mPlaylist);
        }

        @Override
        public void onRemoveQueueItem(MediaDescriptionCompat description) {
            Log.d(TAG, "onRemoveQueueItem: position in list" + mPlaylist.size());
            mPlaylist.remove(new MediaSessionCompat.QueueItem(description,description.hashCode()));
            mQueueIndex = (mPlaylist.isEmpty()) ? -1 : mQueueIndex;

            mSession.setQueue(mPlaylist);
        }

        private boolean isReadyToPlay(){
            return (!mPlaylist.isEmpty());
        }
    }


    private class MediaPlayerListener implements PlaybackInfoListener {

        private final ServiceManager mServiceManager;

        MediaPlayerListener() {
            mServiceManager = new ServiceManager();
        }


        @Override
        public void updateUI(String newMediaId) {
            Log.d(TAG, "updateUI: ");
            Intent intent = new Intent();
            intent.setAction(getString(R.string.broadcast_update_ui));
            intent.putExtra(getString(R.string.broadcast_new_media_id),newMediaId);
            sendBroadcast(intent);
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            //Report the state to the mediasession
            mSession.setPlaybackState(state);

            switch (state.getState()){
                case PlaybackStateCompat.STATE_PLAYING:
                    mServiceManager.displayNotification(state);
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    mServiceManager.displayNotification(state);
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    mServiceManager.moveServiceOutofStartedState();
                    break;
            }
        }

        @Override
        public void onSeekTo(long progress, long max) {
            Intent intent = new Intent();
            intent.setAction("broadcast_seekbar_update");
            intent.putExtra("SEEK_BAR_PROGRESS",progress);
            intent.putExtra("SEEK_BAR_MAX", max);

            sendBroadcast(intent);

        }

        @Override
        public void onPlaybackComplete() {
            //stop the song at end
            Log.d(TAG, "onPlaybackComplete: called");
            //mSession.getController().getTransportControls().skipToNext();
            mSession.getController().getTransportControls().stop();
        }

        class ServiceManager{
            private PlaybackStateCompat mState;

            public  ServiceManager(){}

            public void displayNotification(PlaybackStateCompat state){
                Notification notification = null;

                switch (state.getState()){
                    case PlaybackStateCompat.STATE_PLAYING:{
                        notification = mMediaNotificationManager.buildNotification(
                                state,getSessionToken(),mPlayback.getCurrentMedia().getDescription(),null
                        );
                        if(!mIsServiceRunning){
                            ContextCompat.startForegroundService(MediaService.this,
                                    new Intent(MediaService.this, MediaService.class));
                            mIsServiceRunning = true;
                        }

                        startForeground(MediaNotificationManager.NOTIFICATION_ID,notification);
                        break;
                    }
                    case PlaybackStateCompat.STATE_PAUSED:{
                       stopForeground(false);
                        notification = mMediaNotificationManager.buildNotification(
                                state,getSessionToken(),mPlayback.getCurrentMedia().getDescription(),null
                        );

                        mMediaNotificationManager.getmNotificationManager().notify(MediaNotificationManager.NOTIFICATION_ID,notification);
                        break;
                    }
                }
            }

            private void moveServiceOutofStartedState(){
                stopForeground(true);
                stopSelf();
                mIsServiceRunning = false;
            }
        }
    }

}
