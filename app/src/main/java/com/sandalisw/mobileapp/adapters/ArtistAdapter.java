package com.sandalisw.mobileapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.models.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private static final String TAG = "ArtistAdapter";
    private Context mContext;
    private List<Artist> mData;
    private ArtistSelection mCardListener;

    public ArtistAdapter(Context mContext, ArtistSelection cardListener) {
        this.mContext = mContext;
        this.mCardListener = cardListener;

    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflator = LayoutInflater.from(viewGroup.getContext());
        view =  mInflator.inflate(R.layout.cardview_preference, viewGroup,false);
        return new ArtistViewHolder(view,mCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ArtistViewHolder viewHolder, int i) {
        viewHolder.artist_name.setText(mData.get(i).getArtistName());
        Glide.with(mContext)
                .load(mData.get(i).getThumbnail())
                .error(R.drawable.ic_launcher_background)
                .into(((ArtistViewHolder)viewHolder).artist_thumbnail);
        viewHolder.artist_thumbnail.setImageResource(mData.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setDataList(List<Artist> data){
        Log.d(TAG, "onChanged: CALLED "+mData.size());
        mData = data;
        notifyDataSetChanged();
    }

    public static class ArtistViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView artist_thumbnail;
        TextView artist_name;
        private ArtistSelection cardListener;

        public ArtistViewHolder(@NonNull View itemView, ArtistSelection cardListener) {
            super(itemView);

            artist_name = (TextView) itemView.findViewById(R.id.artist_name);
            artist_thumbnail = (ImageView) itemView.findViewById(R.id.artist_img);
            this.cardListener = cardListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            cardListener.onCardClick(getAdapterPosition());
        }
    }

    public interface ArtistSelection{

        void onCardClick(int position);
    }
}
