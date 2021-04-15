package com.example.celebrityquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MultipleChoiceRankingPagerAdapter extends FragmentPagerAdapter {

    private int pageCount;

    public MultipleChoiceRankingPagerAdapter(@NonNull FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MultipleChoiceRankingEasyFragment multipleChoiceRankingEasyFragment = new MultipleChoiceRankingEasyFragment();
                return multipleChoiceRankingEasyFragment;

            case 1:
                MultipleChoiceRankingMediumFragment multipleChoiceRankingMediumFragment = new MultipleChoiceRankingMediumFragment();
                return multipleChoiceRankingMediumFragment;

            case 2:
                MultipleChoiceRankingHardFragment multipleChoiceRankingHardFragment = new MultipleChoiceRankingHardFragment();
                return multipleChoiceRankingHardFragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
