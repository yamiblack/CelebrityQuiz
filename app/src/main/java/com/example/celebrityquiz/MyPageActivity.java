package com.example.celebrityquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MyPageActivity extends AppCompatActivity {

    private TextView tvEmail;
    private TabLayout tlMyPage;
    private ViewPager vpMyPage;
    private MyPagePagerAdapter myPagePagerAdapter;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        tvEmail = (TextView) findViewById(R.id.tv_email);
        tlMyPage = (TabLayout) findViewById(R.id.tl_mypage);
        vpMyPage = (ViewPager) findViewById(R.id.vp_mypage);

        auth = FirebaseAuth.getInstance();
        tvEmail.setText(auth.getCurrentUser().getEmail());

        tlMyPage.addTab(tlMyPage.newTab().setText("쉬움"));
        tlMyPage.addTab(tlMyPage.newTab().setText("보통"));
        tlMyPage.addTab(tlMyPage.newTab().setText("어려움"));

        myPagePagerAdapter = new MyPagePagerAdapter(getSupportFragmentManager(), tlMyPage.getTabCount());
        vpMyPage.setAdapter(myPagePagerAdapter);

        vpMyPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlMyPage));

        tlMyPage.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpMyPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                vpMyPage.setCurrentItem(tab.getPosition());
            }
        });


    }

}
