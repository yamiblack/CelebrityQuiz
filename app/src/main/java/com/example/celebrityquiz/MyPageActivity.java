package com.example.celebrityquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MyPageActivity extends AppCompatActivity {

    private TextView tvEmail;
    private TextView tvBestScore;
    private TextView tvAverageScore;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvBestScore = (TextView) findViewById(R.id.tv_bestScore);
        tvAverageScore = (TextView) findViewById(R.id.tv_averageScore);

        auth = FirebaseAuth.getInstance();
        tvEmail.setText(auth.getCurrentUser().getEmail());


    }

}
