package fr.wildcodeschool.mediaplayer;


import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://hcmaslov.d-real.sci-nnov.ru/public/mp3/Queen/Queen%20'Bohemian%20Rhapsody'.mp3";
        final MediaPlayer mediaPlayer = new MediaPlayer();
        ImageButton playBtn = findViewById(R.id.play);
        ImageButton resetBtn = findViewById(R.id.reset);
        ImageButton pauseBtn = findViewById(R.id.pause);
        ImageButton loopBtn = findViewById(R.id.loop);
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final Handler seekBarUpdate = new Handler();
        final Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                seekBarUpdate.postDelayed(this,50);
            }
        };

        try {
            mediaPlayer.setDataSource(url);
            //seekBar.setMin(0);

        } catch (Exception e) {
            e.printStackTrace();
        }



        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int duration = mp.getDuration();
                seekBar.setMax(duration);
                mp.start();
                seekBarUpdate.postDelayed(updateSeekBar, 500);

                View myProgressBar = findViewById(R.id.progressBar);
                myProgressBar.setVisibility(View.INVISIBLE);
                findViewById(R.id.play).setClickable(true);
                findViewById(R.id.reset).setClickable(true);
                findViewById(R.id.pause).setClickable(true);
                findViewById(R.id.loop).setClickable(true);

            }
        });
       // mediaPlayer.release();
        mediaPlayer.prepareAsync();

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(0);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer.isPlaying() && b)
                    mediaPlayer.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
