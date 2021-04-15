package com.example.celebrityquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RankingActivity extends AppCompatActivity {

    private Context context = this;

    private Button btnMultipleChoiceRanking;
    private Button btnWordQuizRanking;

    private Intent intent;
    private int rankingType;

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        btnMultipleChoiceRanking = (Button) findViewById(R.id.btn_multipleChoiceRanking);
        btnWordQuizRanking = (Button) findViewById(R.id.btn_wordQuizRanking);

        soundPlayer = new SoundPlayer(this);

        intent = new Intent(this, RankingDetailActivity.class);

        btnMultipleChoiceRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.playSelectSound();
                rankingType = 1;
                intent.putExtra("rankingType", rankingType);
                startActivity(intent);
            }
        });

        btnWordQuizRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.playSelectSound();
                rankingType = 2;
                intent.putExtra("rankingType", rankingType);
                startActivity(intent);
            }
        });
    }
}
