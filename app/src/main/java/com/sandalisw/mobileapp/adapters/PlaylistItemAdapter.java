package com.sandalisw.mobileapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sandalisw.mobileapp.R;

import java.util.List;

public class PlaylistItemAdapter extends RecyclerView.Adapter<PlaylistItemAdapter.PlaylistItemViewHolder> {

    private static final String TAG = "PlatlistAdapter";
    private PlaylistListener songListener;
    private Context mContext;
    private List<MediaMetadataCompat> mData;
    private int mIndex;

    public PlaylistItemAdapter(Context context, PlaylistListener mSongListener){
        this.mContext = context;
        this.songListener = mSongListener;
    }

    @NonNull
    @Override
    public PlaylistItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflator = LayoutInflater.from(viewGroup.getContext());
        view =  mInflator.inflate(R.layout.layout_search_item, viewGroup,false);
        return new PlaylistItemViewHolder(view,songListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistItemViewHolder viewHolder, int i) {
        viewHolder.song_title.setText(mData.get(i).getDescription().getTitle());
        viewHolder.artist_title.setText(mData.get(i).getDescription().getSubtitle());
        Glide.with(mContext)
                .load(mData.get(i).getDescription().getIconUri())
                .error(R.drawable.ic_launcher_background)
                .into(((PlaylistItemViewHolder)viewHolder).thumbnail);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex = viewHolder.getAdapterPosition();
                songListener.onSongClick(mIndex);

               // notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setDataList(List<MediaMetadataCompat> data){
        Log.d(TAG, "onChanged: CALLED "+getItemCount());
        mData = data;
        notifyDataSetChanged();
    }


    public class PlaylistItemViewHolder  extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView song_title;
        TextView artist_title;
        ImageView nowPlaying;

        PlaylistItemViewHolder(@NonNull View itemView, PlaylistItemAdapter.PlaylistListener listener) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            song_title = itemView.findViewById(R.id.song_name);
            artist_title = itemView.findViewById(R.id.artist_name);
            nowPlaying = itemView.findViewById(R.id.options);
            songListener = listener;
        }

    }

    public interface PlaylistListener{
        void onSongClick(int position);
    }

}