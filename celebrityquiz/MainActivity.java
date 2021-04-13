package com.example.celebrityquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare Variables
    private RadioButton radioButtonLevelOne;
    private RadioButton radioButtonLevelTwo;
    private RadioButton radioButtonLevelThree;
    private RadioButton radioButton30;
    private RadioButton radioButton60;
    private RadioButton radioButton90;
    private ProgressBar progressBarDownload;
    private Button buttonStartQuiz;
    public int level;
    public int seconds;
    private SoundPlayer sound;
    MediaPlayer backgroundmusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define Level views
        radioButtonLevelOne = findViewById(R.id.radioButtonLevelOne);
        radioButtonLevelTwo = findViewById(R.id.radioButtonLevelTwo);
        radioButtonLevelThree = findViewById(R.id.radioButtonLevelThree);
        radioButtonLevelOne.setChecked(true);
        radioButtonLevelTwo.setChecked(false);
        radioButtonLevelThree.setChecked(false);

        // Define Time views
        radioButton30 = findViewById(R.id.radioButton30);
        radioButton60 = findViewById(R.id.radioButton60);
        radioButton90 = findViewById(R.id.radioButton90);
        radioButton30.setChecked(true);
        radioButton60.setChecked(false);
        radioButton90.setChecked(false);

        // Define Download views
        progressBarDownload = findViewById(R.id.progressBarDownload);
        progressBarDownload.setMax(100);

        // Define Update and Starting buttons
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        buttonUpdate.setEnabled(true);
        buttonStartQuiz.setEnabled(false);
        downloadTask = null; // Always initialize task to null

        //Start background music
        backgroundmusic = MediaPlayer.create(MainActivity.this, R.raw.bgm);
        backgroundmusic.setLooping(true);
        backgroundmusic.start();
        sound = new SoundPlayer(this);
    }


    private DownloadTask downloadTask;

    // Define Download methods
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            progressBarDownload.setProgress(progress);
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            progressBarDownload.setProgress(progressBarDownload.getMax());
            buttonStartQuiz.setEnabled(true); // Enable Start button when download is successful
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //when download failed, close the foreground notification and create a new one about the failure
            Toast.makeText(getApplicationContext(), "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    public void onButtonUpdate(View view) {
        if(downloadTask == null) {
            // Import data from internet
            String jsonUrl = "https://api.jsonbin.io/b/5e8f60bb172eb6438960f731";
            downloadTask = new DownloadTask(downloadListener, this);
            downloadTask.execute(jsonUrl);
        }

        sound.playSelectSound();
    }

    // Start QuizActivity with user settings/choices
    public void onButtonStartQuiz(View view) {

        if(radioButtonLevelOne.isChecked()) level = 1;
        if(radioButtonLevelTwo.isChecked()) level = 2;
        if(radioButtonLevelThree.isChecked()) level = 3;

        if(radioButton30.isChecked()) seconds = 30;
        if(radioButton60.isChecked()) seconds = 60;
        if(radioButton90.isChecked()) seconds = 90;

        sound.playStartSound();

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("seconds", seconds);
        startActivity(intent);
    }
}