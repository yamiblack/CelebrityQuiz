package com.example.celebrityquiz;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    private Context context = this;
    private Intent intent;

    private RadioButton radioButtonLevelOne;
    private RadioButton radioButtonLevelTwo;
    private RadioButton radioButtonLevelThree;
    private RadioButton radioButton30;
    private RadioButton radioButton60;
    private RadioButton radioButton90;
    private RadioButton radioButtonMultipleChoice;
    private RadioButton radioButtonWordQuiz;
    private ProgressBar progressBarDownload;
    private ToggleButton toggleButtonMusic;
    private Button buttonLogOut;

    private String[] jsonUrl;
    public int level;
    public int seconds;
    public int gameType;

    private SoundPlayer soundPlayer;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        soundPlayer = new SoundPlayer(this);

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

        radioButtonMultipleChoice = findViewById(R.id.radioButtonMultipleChoice);
        radioButtonWordQuiz = findViewById(R.id.radioButtonWordQuiz);
        radioButtonMultipleChoice.setChecked(true);
        radioButtonWordQuiz.setChecked(false);

        // Define Download views
        progressBarDownload = findViewById(R.id.progressBarDownload);
        progressBarDownload.setMax(100);

        // Define Update and Starting buttons
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setEnabled(true);
        downloadTask = null; // Always initialize task to null

        toggleButtonMusic = findViewById(R.id.toggleMusic);
        toggleButtonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMusic = new Intent(getApplicationContext(), MusicService.class);

                if (toggleButtonMusic.isChecked() == true) {
                    startService(intentMusic);
                } else {
                    stopService(intentMusic);
                }

            }
        });

        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPlayer.playSelectSound();
                FirebaseAuth.getInstance().signOut();
                startLogInActivity();
            }
        });
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
            Toast.makeText(getApplicationContext(), "게임 설정 완료", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //when download failed, close the foreground notification and create a new one about the failure
            Toast.makeText(getApplicationContext(), "다운로드 실패", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(getApplicationContext(), "다운로드 일시정지", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            Toast.makeText(getApplicationContext(), "다운로드 취소", Toast.LENGTH_SHORT).show();
        }
    };

    public void onButtonUpdate(View view) {
        soundPlayer.playSelectSound();
        if (downloadTask == null) {
            jsonUrl = new String[3];
            jsonUrl[0] = "https://api.jsonbin.io/b/5e8f60bb172eb6438960f731";
            jsonUrl[1] = "https://api.jsonbin.io/b/607864715b165e19f620c32b";
            jsonUrl[2] = "https://api.jsonbin.io/b/607805a35b165e19f62090f3/4";

            downloadTask = new DownloadTask(downloadListener, this);
            downloadTask.execute(jsonUrl[(int) (Math.random() * 3)]);

            if (radioButtonLevelOne.isChecked()) level = 1;
            if (radioButtonLevelTwo.isChecked()) level = 2;
            if (radioButtonLevelThree.isChecked()) level = 3;

            if (radioButton30.isChecked()) seconds = 30;
            if (radioButton60.isChecked()) seconds = 60;
            if (radioButton90.isChecked()) seconds = 90;

            if (radioButtonMultipleChoice.isChecked()) gameType = 1;
            if (radioButtonWordQuiz.isChecked()) gameType = 2;

            intent = new Intent(this, MainActivity.class);
            intent.putExtra("level", level);
            intent.putExtra("seconds", seconds);
            intent.putExtra("gameType", gameType);
            intent.putExtra("settings", 1);
            intent.putExtra("isNew", 0);
            Toast.makeText(getApplicationContext(), "잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLogInActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}