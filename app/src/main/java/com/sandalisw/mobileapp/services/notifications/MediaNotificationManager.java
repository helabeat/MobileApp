package com.sandalisw.mobileapp.services.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.services.MediaService;
import com.sandalisw.mobileapp.ui.MainActivity;

public class MediaNotificationManager {
    private static final String TAG = "MediaNotificationManage";
    private final MediaService mMediaService;
    private final NotificationManager mNotificationManager;
    private static int REQUEST_CODE = 101;
    public static final int NOTIFICATION_ID = 201;
    private static final String CHANNEL_ID = "com.sandalisw.mobileapp.audioplayer.channel";

    private final NotificationCompat.Action mPlayAction;
    private final NotificationCompat.Action mPaluseAction;
    private final NotificationCompat.Action mNextAction;
    private final NotificationCompat.Action mPrevAction;

    public MediaNotificationManager(MediaService mediaService){
        this.mMediaService = mediaService;

        mNotificationManager = (NotificationManager) mMediaService.getSystemService(Context.NOTIFICATION_SERVICE);

        mPlayAction = new NotificationCompat.Action(
                R.drawable.ic_play_arrow_white_24dp,
                mMediaService.getString(R.string.label_play),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mMediaService,
                        PlaybackStateCompat.ACTION_PLAY
                )
        );

        mPaluseAction = new NotificationCompat.Action(
                R.drawable.ic_pause_circle_outline_white_24dp,
                mMediaService.getString(R.string.label_pause),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mMediaService,
                        PlaybackStateCompat.ACTION_PAUSE
                )
        );

        mNextAction = new NotificationCompat.Action(
                R.drawable.ic_skip_next_white_24dp,
                mMediaService.getString(R.string.label_previous),null
//                MediaButtonReceiver.buildMediaButtonPendingIntent(
//                        mMediaService,
//                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT
//                )
        );

        mPrevAction = new NotificationCompat.Action(
                R.drawable.ic_skip_previous_white_24dp,
                mMediaService.getString(R.string.label_previous),null
//                MediaButtonReceiver.buildMediaButtonPendingIntent(
//                        mMediaService,
//                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
//                )
        );

        mNotificationManager.cancelAll();
    }

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel(){
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            // The user-visible name of the channel.
            CharSequence name = "MediaSession";
            // The user-visible description of the channel.
            String description = "MediaSession and MediaPlayer";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(
                    new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            Log.d(TAG, "createChannel: New channel created");
        } else {
            Log.d(TAG, "createChannel: Existing channel reused");
        }
    }

    private boolean isAndroidOOrHigher(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public Notification buildNotification(@NonNull PlaybackStateCompat state,
                                          MediaSessionCompat.Token token,
                                          final MediaDescriptionCompat description,
                                          Bitmap bitmap
                                          ){

        boolean isPlaying = state.getState() == PlaybackStateCompat.STATE_PLAYING;

        if(isAndroidOOrHigher()){
            createChannel();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mMediaService, CHANNEL_ID);
        builder.setStyle(
                new android.support.v4.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(token)
                .setShowActionsInCompactView(0,1,2)//play pause skip buttons
        )
                .setColor(ContextCompat.getColor(mMediaService, R.color.colorPrimaryBackground))
                .setSmallIcon(R.drawable.ic_guitar)
                .setContentIntent(createContentIntent())
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setLargeIcon(bitmap)
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mMediaService,PlaybackStateCompat.ACTION_STOP
                ))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        if((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) != 0){
            builder.addAction(mPrevAction);
        }

        builder.addAction(isPlaying ? mPaluseAction : mPlayAction);

        if((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) != 0){
            builder.addAction(mNextAction);
        }

        return builder.build();
    }

    private PendingIntent createContentIntent(){
        Intent openUI = new Intent(mMediaService, MainActivity.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(mMediaService,501, openUI, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
