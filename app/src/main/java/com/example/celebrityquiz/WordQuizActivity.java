package com.example.celebrityquiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WordQuizActivity extends AppCompatActivity {

    // Declare variables
    private List<Quiz> quizList;
    private int seconds;
    private int indexCurrentQuestion;

    private TextView questionView;
    private ImageView imageView;
    private Button buttonPrevious;
    private Button buttonNext;
    private TextView textTime;
    private CountDownTimer countDownTimer;
    private LinearLayout textContainer;
    private TextView view[] = new TextView[17];
    private Button btn[] = new Button[20];
    private int btnNum[] = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10,
            R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15,
            R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20};
    private int cntText = 0;
    private char[] btnArr = new char[20];
    private String answerText = "";
    private char[] answerArr;
    private List<String> correctAnswerList;

    private List<String> userAnswerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_wordquiz);

        // Hide toolbar
//        Objects.requireNonNull(getSupportActionBar()).hide();

        // Define Activity views
        questionView = findViewById(R.id.celebrityQuestion);
        imageView = findViewById(R.id.celebrityImage);

        textTime = findViewById(R.id.textTime);

        // Define button views
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        // Access intent interface and get variables
        Intent intent = getIntent();
        int level = intent.getIntExtra("level", 1);
        seconds = intent.getIntExtra("seconds", 30);
        String string = intent.getStringExtra("string");
//        String string = null;

        // Safely read data from saved file
//        try {
//            FileInputStream fileInputStream = openFileInput("myJson");
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            string = stringBuilder.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Quiz>>() {
        }.getType();
        List<Quiz> list = gson.fromJson(string, type);

        // Set sublist based on user set level
        if (level == 1) {
            assert list != null;
            quizList = list.subList(0, 5);
        } else if (level == 2) {
            assert list != null;
            quizList = list.subList(5, 10);
        } else {
            assert list != null;
            quizList = list.subList(10, 15);
        }

        correctAnswerList = new ArrayList<>();
        for(int i = 0; i<quizList.size(); i++)
            correctAnswerList.add(getCurrentAnswer(quizList.get(i)));

        textContainer = findViewById(R.id.answerLayout);

        // initialise and set for each index in current activity as current question
        indexCurrentQuestion = 0;
        Quiz currentQuestion = quizList.get(indexCurrentQuestion);
        currentQuestionView(currentQuestion);
        buttonPrevious.setEnabled(false); // Disable previous button when current index is 0

        // answerText 생성
        userAnswerList = new ArrayList<>();

        answerArr = getCurrentAnswer(currentQuestion).toCharArray();
        createAnswerText(answerArr.length);

        //랜덤 버튼 생성
        setButton(answerArr);

        // See function
        startTimer();

        // When user submit quiz, stop time and start Solution Activity
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();

                userAnswerList.add(answerText);
                if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                    quizList.get(indexCurrentQuestion).userAnswer = quizList.get(indexCurrentQuestion).correctAnswer;
                }

                Intent i = new Intent(WordQuizActivity.this, WordSolutionActivity.class);
                i.putExtra("score", getScore());
                // Change List to ArrayList to accommodate subList
                ArrayList<Quiz> list = new ArrayList<>(quizList);
                ArrayList<String> answerList = new ArrayList<>(userAnswerList);
                ArrayList<String> correctAnswer = new ArrayList<>(correctAnswerList);
                i.putExtra("quizList", list);
                i.putExtra("userAnswerList", answerList);
                i.putExtra("correctAnswerList", correctAnswer);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
    }

    // Start countdown. OnFinish, start Solution Activity
    public void startTimer() {
        textTime.setText(String.valueOf(seconds));
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textTime.setText(String.valueOf((int) (millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {

                userAnswerList.add(answerText);
                if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                    quizList.get(indexCurrentQuestion).userAnswer = quizList.get(indexCurrentQuestion).correctAnswer;
                }

                Intent i = new Intent(WordQuizActivity.this, WordSolutionActivity.class);
                i.putExtra("score", getScore());
                // Change List to ArrayList to accommodate subList
                ArrayList<Quiz> list = new ArrayList<>(quizList);
                ArrayList<String> answerList = new ArrayList<>(userAnswerList);
                ArrayList<String> correctAnswer = new ArrayList<>(correctAnswerList);
                i.putExtra("quizList", list);
                i.putExtra("userAnswerList", answerList);
                i.putExtra("correctAnswerList", correctAnswer);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        }.start();
    }

    // Cancel timer to prevent countDown in background
    // If not defined, Solution Activity will start even when user goes back to
    // Main Activity because Quiz Activity doesn't get destroyed instantly
    public void stopTimer() {
        countDownTimer.cancel();
    }

    // Pre-define new views before setting previous question as current question, for index < 0
    public void onButtonPrevious(View view) {

        if (indexCurrentQuestion != 0) {
            int size = this.userAnswerList.size();
            size = size - 1;
            this.userAnswerList.remove(size);

            indexCurrentQuestion--;
            if (indexCurrentQuestion == 0) buttonPrevious.setEnabled(false);
            if (indexCurrentQuestion != (quizList.size() - 1)) buttonNext.setEnabled(true);
            Quiz currentQuestion = quizList.get(indexCurrentQuestion);
            currentQuestionView(currentQuestion);

            answerArr = getCurrentAnswer(currentQuestion).toCharArray();
            createAnswerText(answerArr.length);
            setButton(answerArr);
            answerText = "";
            cntText = 0;
        }
    }

    // Pre-define new views before setting next question as current question, for index > list.size()
    public void onButtonNext(View view) {
        if (indexCurrentQuestion != (quizList.size() - 1)) {
            this.userAnswerList.add(answerText);
            if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                quizList.get(indexCurrentQuestion).userAnswer = quizList.get(indexCurrentQuestion).correctAnswer;
            }

            indexCurrentQuestion++;
            if (indexCurrentQuestion == (quizList.size() - 1)) buttonNext.setEnabled(false);
            if (indexCurrentQuestion != 0) buttonPrevious.setEnabled(true);
            Quiz currentQuestion = quizList.get(indexCurrentQuestion);
            currentQuestionView(currentQuestion);

            answerArr = getCurrentAnswer(currentQuestion).toCharArray();
            createAnswerText(answerArr.length);
            setButton(answerArr);
            answerText = "";
            cntText = 0;
        }
    }

    public void currentQuestionView(Quiz currentQuestion) {
        questionView.setText(String.format("%s. %s", indexCurrentQuestion + 1, currentQuestion.question));

        Glide.with(imageView.getContext()).load(currentQuestion.imageUrl).into(imageView);
    }

    // Calculate score
    public int getScore() {
        int score = 0;
        for (int i = 0; i < quizList.size(); i++) {
            if (quizList.get(i).userAnswer == quizList.get(i).correctAnswer) score++;
        }
        return score;
    }

    public void createAnswerText(int n) {
        textContainer.removeAllViews();
        for (int i = 0; i < 17; i++) {
            view[i] = new TextView(this);
        }
        for (int i = 0; i < n; i++) {
            view[i].setText("_");
            view[i].setTextSize(23);
            view[i].setTextColor(Color.BLACK);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            view[i].setLetterSpacing(0.3f);
            view[i].setLayoutParams(lp);

            //부모 뷰에 추가
            textContainer.addView(view[i]);
        }
    }

    private String getCurrentAnswer(Quiz currentQuestion) {

        String correctAnswer = null;
        switch (currentQuestion.correctAnswer) {
            case 1: {
                correctAnswer = String.format("%s", currentQuestion.one);
                break;
            }
            case 2: {
                correctAnswer = String.format("%s", currentQuestion.two);
                break;
            }
            case 3: {
                correctAnswer = String.format("%s", currentQuestion.three);
                break;
            }
            case 4: {
                correctAnswer = String.format("%s", currentQuestion.four);
                break;
            }
        }
        correctAnswer = correctAnswer.toUpperCase();
        correctAnswer = correctAnswer.replaceAll(" ", "");
        return correctAnswer;
    }

    private void setButton(final char[] answerArr) {
        for (int i = 0; i < answerArr.length; i++)
            btnArr[i] = answerArr[i];

        for (int i = answerArr.length; i < btnArr.length; i++) {
            btnArr[i] = (char) ((Math.random() * 26) + 65);
        }

        btnArr = shuffle(btnArr);

        // setOnClickListener and set checked onClick for each button
        for (int i = 0; i < 20; i++) {
            btn[i] = findViewById(btnNum[i]);
            btn[i].setText(String.valueOf(btnArr[i]));
        }

        for (int i = 0; i < 20; i++) {
            btn[i].setEnabled(true);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < 20; i++) {
                        if (v.getId() == btn[i].getId())
                            if (cntText < answerArr.length) {
                                view[cntText].setText(btn[i].getText());
                                btn[i].setEnabled(false);
                                answerText = answerText + view[cntText].getText().toString();
                                cntText++;
                            }
                    }
                }
            });
        }
    }

    private char[] shuffle(char[] arr) {
        for (int x = 0; x < arr.length; x++) {
            int i = (int) (Math.random() * arr.length);
            int j = (int) (Math.random() * arr.length);

            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
