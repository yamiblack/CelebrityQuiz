package com.example.celebrityquiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordQuizActivity extends AppCompatActivity {

    // Declare variables
    private List<Quiz> quizList;
    private int level;
    private int seconds;
    private int gameType;
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
    private ImageView[] heart = new ImageView[3];

    private int heartCnt = 0;

    private int leftTime;
    private int currentTime;

    private List<String> userAnswerList;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

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
        heart[0] = findViewById(R.id.heart1);
        heart[1] = findViewById(R.id.heart2);
        heart[2] = findViewById(R.id.heart3);

        // Define button views
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Access intent interface and get variables
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 1);
        seconds = intent.getIntExtra("seconds", 30);
        gameType = intent.getIntExtra("gameType", 2);
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
        for (int i = 0; i < quizList.size(); i++)
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

//                setIncorrectNoteDB();
                setRankingDB();

                userAnswerList.add(answerText);
                if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                    quizList.get(indexCurrentQuestion).userAnswer = quizList.get(indexCurrentQuestion).correctAnswer;
                }

                Intent i = new Intent(WordQuizActivity.this, WordQuizSolutionActivity.class);
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
                leftTime = (int) (millisUntilFinished / 1000);
                currentTime = seconds - leftTime;
                textTime.setText(String.valueOf(leftTime));
            }

            @Override
            public void onFinish() {

                setRankingDB();
//                setIncorrectNoteDB();

                userAnswerList.add(answerText);
                if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                    quizList.get(indexCurrentQuestion).userAnswer = quizList.get(indexCurrentQuestion).correctAnswer;
                }

                Intent i = new Intent(WordQuizActivity.this, WordQuizSolutionActivity.class);
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
            Quiz currentQuestion = quizList.get(indexCurrentQuestion);
            currentQuestionView(currentQuestion);

            if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                this.userAnswerList.add(answerText);
                if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                    quizList.get(indexCurrentQuestion).userAnswer = quizList.get(indexCurrentQuestion).correctAnswer;
                }

                indexCurrentQuestion++;
                if (indexCurrentQuestion == (quizList.size() - 1)) buttonNext.setEnabled(false);
                if (indexCurrentQuestion != 0) buttonPrevious.setEnabled(true);
                currentQuestion = quizList.get(indexCurrentQuestion);
                currentQuestionView(currentQuestion);

                answerArr = getCurrentAnswer(currentQuestion).toCharArray();
                createAnswerText(answerArr.length);
                setButton(answerArr);
                answerText = "";
                cntText = 0;
            }

            else {
                heartCounter();
            }
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
                    for (int j = 0; j < 20; j++) {
                        if (v.getId() == btn[j].getId())
                            if (cntText < answerArr.length) {
                                if(btn[j].getText().charAt(0) == answerArr[cntText]) {
                                    view[cntText].setText(btn[j].getText());
                                    btn[j].setEnabled(false);
                                    answerText = answerText + view[cntText].getText().toString();
                                    cntText++;
                                }
                                else {
                                    heartCounter();
                                }
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
    private void heartCounter() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        switch (this.heartCnt){
            case 0: {
                heart[0].setBackgroundResource(R.drawable.icon_heart_disable);
                heart[0].startAnimation(shake);
                break;
            }
            case 1: {
                heart[1].setBackgroundResource(R.drawable.icon_heart_disable);
                heart[0].startAnimation(shake);
                heart[1].startAnimation(shake);
                break;
            }
            case 2: {
                heart[2].setBackgroundResource(R.drawable.icon_heart_disable);
                heart[0].startAnimation(shake);
                heart[1].startAnimation(shake);
                heart[2].startAnimation(shake);
                break;
            }
        }
        heartCnt++;
        if(heartCnt == 3 ) {

            stopTimer();

            setRankingDB();
//            setIncorrectNoteDB();

            userAnswerList.add(answerText);
            if (answerText.equals(getCurrentAnswer(quizList.get(indexCurrentQuestion)))) {
                quizList.get(indexCurrentQuestion).userAnswer = quizList.get(indexCurrentQuestion).correctAnswer;
            }

            Intent i = new Intent(WordQuizActivity.this, WordQuizSolutionActivity.class);
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
    }
    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setRankingDB() {
        Map<String, Object> data = new HashMap<>();
        data.put("email", auth.getCurrentUser().getEmail());
        data.put("gameType", String.valueOf(gameType));
        data.put("level", String.valueOf(level));
        data.put("score", String.valueOf(getScore()));
        data.put("time", String.valueOf(currentTime));

        db.collection("RANKING")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public void setIncorrectNoteDB() {

        for(int i = 0; i < quizList.size(); i++) {
            if(quizList.get(i).correctAnswer != quizList.get(i).userAnswer) {
                Map<String, Object> data = new HashMap<>();
                data.put("email", auth.getCurrentUser().getEmail());
                data.put("imageURL", quizList.get(i).imageUrl);
                data.put("answer", quizList.get(i).correctAnswer);

                db.collection("INCORRECTNOTE")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        }
    }
}
