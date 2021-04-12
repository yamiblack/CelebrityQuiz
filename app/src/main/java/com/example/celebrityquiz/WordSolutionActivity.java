package com.example.celebrityquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.celebrityquiz.R;
import com.example.celebrityquiz.WordSolutionAdapter;
import com.example.celebrityquiz.Quiz;

import java.util.List;
import java.util.Objects;

public class WordSolutionActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordsolution);

//        // Define Navigation
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Results");
//        }

        // Interface instance to get values from QuizActivity
        int scoreValue = getIntent().getIntExtra("score", 0);
        List<Quiz> quizList = (List<Quiz>) getIntent().getSerializableExtra("quizList");
        List<String> userAnswerText = (List<String>) getIntent().getSerializableExtra("userAnswerList");
        List<String> correctAnswerList = (List<String>) getIntent().getSerializableExtra("correctAnswerList");
        // Set view and display scoreValue
        TextView scoreView = findViewById(R.id.scoreTextView);
        scoreView.setText(String.valueOf(scoreValue));

        // Set score out-of view
        TextView scoreTotalView = findViewById(R.id.scoreTotalTextView);
        scoreTotalView.setText(String.valueOf(5));

        // See function
        displayWellDone(scoreValue);

        // RecycleView definitions
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        WordSolutionAdapter wordSolutionAdapter = new WordSolutionAdapter(quizList, this, userAnswerText, correctAnswerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordSolutionAdapter);
    }

    // Function to display well done image if user gets all correct | also settings for total value
    public void displayWellDone(int score) {

        // Set view for well done image
        ImageView imageView = findViewById(R.id.wellDoneImage);
        imageView.setVisibility(View.INVISIBLE); // set image invisible

        // display well done image if user gets all correct
        if (score == 5) imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
