package com.sandalisw.mobileapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.models.Song;

import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder> {

    private static final String TAG = "SearchItemAdapter";

    private Context mContext;
    private SongResultSelection songListener;
    private List<Song> mData;

    public SearchItemAdapter(Context context, SongResultSelection mSongListener){
        this.mContext = context;
        this.songListener = mSongListener;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflator = LayoutInflater.from(viewGroup.getContext());
        view =  mInflator.inflate(R.layout.layout_search_item, viewGroup,false);
        return new SearchItemViewHolder(view,songListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: "+ mData.get(i).getArtist());
        viewHolder.song_title.setText(mData.get(i).getTitle());
        viewHolder.artist_title.setText(mData.get(i).getArtist());
        Glide.with(mContext)
                .load(mData.get(i).getThumbnailUrl())
                .error(R.drawable.ic_launcher_background)
                .into(((SearchItemViewHolder)viewHolder).thumbnail);
    }


    public void setDataList(List<Song> data){
        Log.d(TAG, "onChanged: CALLED "+getItemCount());
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public class SearchItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thumbnail;
        TextView song_title;
        TextView artist_title;
        private SongResultSelection songListener;

        public SearchItemViewHolder(@NonNull View itemView, SongResultSelection listener) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            song_title = itemView.findViewById(R.id.song_name);
            artist_title = itemView.findViewById(R.id.artist_name);
            this.songListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: song selected");
            songListener.onSongClick(getAdapterPosition());
        }
    }

    public interface SongResultSelection{
        void onSongClick(int position);
    }
}
