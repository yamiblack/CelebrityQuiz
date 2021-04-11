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

public class GlobalRankingActivity extends AppCompatActivity {

    private Button btnMultipleChoiceRanking;
    private Button btnWordQuizRanking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        btnMultipleChoiceRanking = (Button) findViewById(R.id.btn_multipleChoiceRanking);
        btnWordQuizRanking = (Button) findViewById(R.id.btn_wordQuizRanking);

        btnMultipleChoiceRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Test : ", "성공");
            }
        });

        btnWordQuizRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Test : ", "성공");
            }
        });

    }

}
