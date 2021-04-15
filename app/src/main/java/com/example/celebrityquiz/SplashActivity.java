package com.example.celebrityquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;


public class SplashActivity extends Activity {
    private final int SPLASH_TIME = 1500;
    private Intent intentMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        intentMain = new Intent(this, MainActivity.class);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent((getApplication()), LogInActivity.class));
                } else {
                    intentMain.putExtra("isNew", 1);
                    startActivity(intentMain);
                }
            }
        }, SPLASH_TIME);

    }

}
