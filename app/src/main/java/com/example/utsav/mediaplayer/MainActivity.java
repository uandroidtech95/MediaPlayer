package com.example.utsav.mediaplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.utsav.mediaplayer.adapter.MusicAdapter;
import com.example.utsav.mediaplayer.model.Music;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvSong;
    public static final int MY_PERMISSION = 1;
    ArrayList<Music> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvSong = (RecyclerView) findViewById(R.id.rv_song);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION);

            }
        } else {
            dostuff();
        }
    }

    private void dostuff() {
        getMusic();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rvSong.setLayoutManager(layoutManager);
        MusicAdapter musicAdapter = new MusicAdapter(arrayList);
        rvSong.setAdapter(musicAdapter);
    }


    public void getMusic() {
        ContentResolver resolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = resolver.query(musicUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                Music music = new Music();
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(artist);
                music.setMusic(currentTitle + "\n" + currentArtist);
                arrayList.add(music);
            } while (songCursor.moveToNext());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                        dostuff();
                    } else {
                        Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    return;
                }
        }
    }
}
