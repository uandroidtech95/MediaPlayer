package com.example.utsav.mediaplayer.adapter;

import android.media.MediaPlayer;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.utsav.mediaplayer.R;
import com.example.utsav.mediaplayer.model.Music;

import java.util.ArrayList;

/**
 * Created by utsav on 16-06-2017.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicAdapterHolder> {
MediaPlayer mediaPlayer=new MediaPlayer();
    ArrayList<Music> musicArrayList = new ArrayList<>();

    public MusicAdapter(ArrayList<Music> musicArrayList) {
        this.musicArrayList = musicArrayList;
    }

    @Override
    public MusicAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_music, parent, false);
        return new MusicAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicAdapterHolder holder, int position) {
        Music music = musicArrayList.get(position);
        holder.tvMusic.setText(music.getMusic());

    }

    @Override
    public int getItemCount() {
        return musicArrayList.size();
    }

    public class MusicAdapterHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvMusic;

        public MusicAdapterHolder(View itemView) {
            super(itemView);
            tvMusic = (AppCompatTextView) itemView.findViewById(R.id.tv_music);
        }
    }
}
