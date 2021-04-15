package com.example.celebrityquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WordQuizRankingPagerAdapter extends FragmentPagerAdapter {

    private int pageCount;

    public WordQuizRankingPagerAdapter(@NonNull FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                WordQuizRankingEasyFragment wordQuizRankingEasyFragment = new WordQuizRankingEasyFragment();
                return wordQuizRankingEasyFragment;

            case 1:
                WordQuizRankingMediumFragment wordQuizRankingMediumFragment = new WordQuizRankingMediumFragment();
                return wordQuizRankingMediumFragment;

            case 2:
                WordQuizRankingHardFragment wordQuizRankingHardFragment = new WordQuizRankingHardFragment();
                return wordQuizRankingHardFragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
