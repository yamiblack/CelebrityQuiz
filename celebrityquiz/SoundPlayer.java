package com.example.celebrityquiz;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int startsound;
    private static int selectsound;
    private static int submitsound;

    public SoundPlayer(Context context){

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        startsound = soundPool.load(context, R.raw.startsound, 1);
        selectsound = soundPool.load(context, R.raw.selectsound, 1);
        submitsound = soundPool.load(context, R.raw.submitsound, 1);
    }

    public void playStartSound(){

        //play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(startsound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playSelectSound(){

        soundPool.play(selectsound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playSubmitSound(){

        soundPool.play(submitsound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
