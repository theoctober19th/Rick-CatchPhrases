package com.theoctober19th.audiodemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity { 

    MediaPlayer mediaPlayer = null;
    SeekBar volumeControl = null;
    SeekBar scrubSeekBar = null;
    AudioManager audioManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating a media player for playing audio
        //default audio is "is anyone here"
        mediaPlayer = MediaPlayer.create(this, R.raw.isanyonehere);

        volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);
        scrubSeekBar = (SeekBar) findViewById(R.id.scrubSeekBar);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //get the maximum volume capacity of this device
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //get current volumeLevel in the device
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //set the maximum length of the seekbar to audio length
        scrubSeekBar.setMax(mediaPlayer.getDuration());
        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });

        //schedule a timer so that seek bar updates it itself every few milliseconds to be corresponding to the position of audio
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 300);
    }

    public void playMedia(View view){
        mediaPlayer.start();
    }

    public void pauseMedia(View view){
        mediaPlayer.pause();
    }

    public void fastForwardMedia(View view){
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 1000);
    }

    public void fastRewindMedia(View view){
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 1000);
    }

    public void changeTrack(View view){
        if(view.getId() == R.id.andThatsTheButton) {
            mediaPlayer = MediaPlayer.create(this, R.raw.andthatsthewaythenewsgoes);
        }else if(view.getId() == R.id.thatsWhatIAlwaysButton){
            mediaPlayer = MediaPlayer.create(this, R.raw.thatswhatialwayssay);
        }else if(view.getId() == R.id.grassTasteBadButton){
            mediaPlayer = MediaPlayer.create(this, R.raw.grasstastebad);
        }else if(view.getId() == R.id.iAmPickleRickButton){
            mediaPlayer = MediaPlayer.create(this, R.raw.iampicklerick);
        }else if(view.getId() == R.id.isAnyoneHereButton){
            mediaPlayer = MediaPlayer.create(this, R.raw.isanyonehere);
        }else if(view.getId() == R.id.rickyTickyButton){
            mediaPlayer = MediaPlayer.create(this, R.raw.rickytickytabbybitch);
        }else if(view.getId() == R.id.robberBabyButton){
            mediaPlayer = MediaPlayer.create(this, R.raw.robberbabybabybunkers);
        }else if(view.getId() == R.id.wobbaLobbaButton){
            mediaPlayer = MediaPlayer.create(this, R.raw.wobalobadubbdubb);
        }
        scrubSeekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
    }
}
