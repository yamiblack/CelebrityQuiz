package com.example.celebrityquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class RankingDetailActivity extends AppCompatActivity {

    private TextView tvRankingType;
    private TabLayout tlRanking;
    private ViewPager vpRanking;
    private MultipleChoiceRankingPagerAdapter multipleChoiceRankingPagerAdapter;
    private WordQuizRankingPagerAdapter wordQuizRankingPagerAdapter;

    private Intent intent;
    private int rankingType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_detail);

        tvRankingType = (TextView) findViewById(R.id.tv_ranking_type);
        tlRanking = (TabLayout) findViewById(R.id.tl_ranking);
        vpRanking = (ViewPager) findViewById(R.id.vp_ranking);

        tlRanking.addTab(tlRanking.newTab().setText("쉬움"));
        tlRanking.addTab(tlRanking.newTab().setText("보통"));
        tlRanking.addTab(tlRanking.newTab().setText("어려움"));

        intent = getIntent();
        rankingType = intent.getIntExtra("rankingType", 0);

        if(rankingType == 0) {
            Toast.makeText(getApplicationContext(), "네트워크 상태를 확인해 주세요.", Toast.LENGTH_SHORT).show();
        } else if(rankingType == 1) {
            tvRankingType.setText("객관식 글로벌 랭킹");
            multipleChoiceRankingPagerAdapter = new MultipleChoiceRankingPagerAdapter(getSupportFragmentManager(), tlRanking.getTabCount());
            vpRanking.setAdapter(multipleChoiceRankingPagerAdapter);
        } else if(rankingType == 2) {
            tvRankingType.setText("워드 퀴즈 글로벌 랭킹");
            wordQuizRankingPagerAdapter = new WordQuizRankingPagerAdapter(getSupportFragmentManager(), tlRanking.getTabCount());
            vpRanking.setAdapter(wordQuizRankingPagerAdapter);
        }

        vpRanking.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlRanking));

        tlRanking.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpRanking.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                vpRanking.setCurrentItem(tab.getPosition());
            }
        });
    }

}
