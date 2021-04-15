package com.example.celebrityquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private long backKeyPressedTime = 0;

    private Button btnGameStart;
    private Button btnGlobalRanking;
    private Button btnMyPage;
    private Button btnIncorrectNote;
    private Button btnGameSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGameStart = (Button) findViewById(R.id.btn_gameStart);
        btnGlobalRanking = (Button) findViewById(R.id.btn_GlobalRanking);
        btnMyPage = (Button) findViewById(R.id.btn_myPage);
        btnIncorrectNote = (Button) findViewById(R.id.btn_IncorrectNote);
        btnGameSettings = (Button) findViewById(R.id.btn_gameSettings);

        btnGameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                int isNew = intent.getIntExtra("isNew", 0);

                if (isNew == 1) {
                    Toast.makeText(getApplicationContext(), "게임 설정을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                }

                int level = intent.getIntExtra("level", 1);
                int seconds = intent.getIntExtra("seconds", 30);
                int gameType = intent.getIntExtra("gameType", 1);
                int settings = intent.getIntExtra("settings", 0);

                String string = null;

                try {
                    FileInputStream fileInputStream = openFileInput("myJson");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    string = stringBuilder.toString();

                    if (gameType == 1) {
                        if (settings == 0) {
                            Toast.makeText(getApplicationContext(), "게임 설정을 하지 않아 기본 모드로 실행됩니다.", Toast.LENGTH_SHORT).show();
                        }
                        intent = new Intent(context, MultipleChoiceActivity.class);
                    } else {
                        intent = new Intent(context, WordQuizActivity.class);
                    }
                    intent.putExtra("gameType", gameType);
                    intent.putExtra("level", level);
                    intent.putExtra("seconds", seconds);
                    intent.putExtra("string", string);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnGlobalRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RankingActivity.class));
            }
        });

        btnMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
            }
        });

        btnIncorrectNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), IncorrectNoteActivity.class));
            }
        });

        btnGameSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }
}
