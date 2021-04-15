package com.example.celebrityquiz;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int startSound;
    private static int selectSound;
    private static int submitSound;

    public SoundPlayer(Context context) {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        startSound = soundPool.load(context, R.raw.startsound, 1);
        selectSound = soundPool.load(context, R.raw.selectsound, 1);
        submitSound = soundPool.load(context, R.raw.submitsound, 1);
    }

    public void playStartSound() {
        soundPool.play(startSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playSelectSound() {
        soundPool.play(selectSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playSubmitSound() {
        soundPool.play(submitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
