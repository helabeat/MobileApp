package com.sandalisw.mobileapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.models.Song;

import java.util.ArrayList;
import java.util.List;

public class RecentSongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecentSongAdapter";

    private Context mContext;
    private List<Song> dataList = new ArrayList<>();
    private SongListener mSongListener;
    private int mCategory;
    private int mIndex = -1;

    public RecentSongAdapter(Context context, SongListener songListener,int category) {
        mContext = context;
        mSongListener = songListener;
        mCategory =  category;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recent_song,viewGroup,false);
        return new RecentSongViewHolder(view,mSongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        Glide.with(mContext)
                .load(dataList.get(i).getThumbnailUrl())
                .error(R.drawable.ic_launcher_background)
                .into(((RecentSongViewHolder)viewHolder).thumbnail);
        ((RecentSongViewHolder)viewHolder).song_title.setText(dataList.get(i).getTitle());
        ((RecentSongViewHolder)viewHolder).artist_title.setText(dataList.get(i).getArtist());

        if(mIndex == i)
            ((RecentSongViewHolder)viewHolder).song_title.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        else
            ((RecentSongViewHolder)viewHolder).song_title.setTextColor(ContextCompat.getColor(mContext,R.color.fontAshDarker));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex = viewHolder.getAdapterPosition();
                mSongListener.onSongClick(mIndex,mCategory);
                notifyDataSetChanged();
            }
        });
    }

    public void setDataList(List<Song> mData){
        Log.d(TAG, "onChanged: CALLED "+mData.size());
        dataList = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(dataList != null){
            return dataList.size();
        }
        return 0;
    }

    private class RecentSongViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView song_title;
        TextView artist_title;


        public RecentSongViewHolder(@NonNull View itemView, SongListener songlistener) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            song_title = itemView.findViewById(R.id.recent_song_name);
            artist_title = itemView.findViewById(R.id.recent_artist_name);
        }

    }

    public interface SongListener{

        void onSongClick(int position,int category);

    }
}
