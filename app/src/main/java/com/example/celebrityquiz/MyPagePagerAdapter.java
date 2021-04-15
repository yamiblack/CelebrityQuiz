package com.example.celebrityquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagePagerAdapter extends FragmentPagerAdapter {

    private int pageCount;

    public MyPagePagerAdapter(@NonNull FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyPageEasyFragment myPageEasyFragment = new MyPageEasyFragment();
                return myPageEasyFragment;

            case 1:
                MyPageMediumFragment myPageMediumFragment = new MyPageMediumFragment();
                return myPageMediumFragment;

            case 2:
                MyPageHardFragment myPageHardFragment = new MyPageHardFragment();
                return myPageHardFragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
