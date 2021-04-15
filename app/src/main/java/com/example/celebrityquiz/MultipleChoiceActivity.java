package com.example.celebrityquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.protobuf.StringValue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleChoiceActivity extends AppCompatActivity {

    Context context = this;

    private List<Quiz> quizList;
    private int level;
    private int seconds;
    private int gameType;
    private int indexCurrentQuestion;

    private TextView questionView;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private RadioButton radioButtonOne;
    private RadioButton radioButtonTwo;
    private RadioButton radioButtonThree;
    private RadioButton radioButtonFour;
    private Button buttonPrevious;
    private Button buttonNext;
    private TextView textTime;
    private CountDownTimer countDownTimer;
    private ImageView[] heart = new ImageView[3];
    ;
    private int heartCnt = 0;

    private int leftTime;
    private int currentTime;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_quiz);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Hide toolbar
//        Objects.requireNonNull(getSupportActionBar()).hide();

        // Define Activity views
        questionView = findViewById(R.id.celebrityQuestion);
        imageView = findViewById(R.id.celebrityImage);
        radioGroup = findViewById(R.id.celebrityOption);
        radioButtonOne = findViewById(R.id.radioButtonOne);
        radioButtonTwo = findViewById(R.id.radioButtonTwo);
        radioButtonThree = findViewById(R.id.radioButtonThree);
        radioButtonFour = findViewById(R.id.radioButtonFour);
        textTime = findViewById(R.id.textTime);
        heart[0] = findViewById(R.id.heart1);
        heart[1] = findViewById(R.id.heart2);
        heart[2] = findViewById(R.id.heart3);
        // setOnClickListener and set checked onClick for each button
        radioButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RadioButton) view).setChecked(true);
                quizList.get(indexCurrentQuestion).userAnswer = 1;
            }
        });

        radioButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RadioButton) view).setChecked(true);
                quizList.get(indexCurrentQuestion).userAnswer = 2;
            }
        });

        radioButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RadioButton) view).setChecked(true);
                quizList.get(indexCurrentQuestion).userAnswer = 3;
            }
        });

        radioButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RadioButton) view).setChecked(true);
                quizList.get(indexCurrentQuestion).userAnswer = 4;
            }
        });

        // Define button views
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        // Access intent interface and get variables
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 1);
        seconds = intent.getIntExtra("seconds", 30);
        gameType = intent.getIntExtra("gameType", 1);
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

        // initialise and set for each index in current activity as current question
        indexCurrentQuestion = 0;
        Quiz currentQuestion = quizList.get(indexCurrentQuestion);
        currentQuestionView(currentQuestion);
        buttonPrevious.setEnabled(false); // Disable previous button when current index is 0

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
                Intent i = new Intent(MultipleChoiceActivity.this, MultipleChoiceSolutionActivity.class);
                i.putExtra("score", getScore());
                // Change List to ArrayList to accommodate subList
                ArrayList<Quiz> list = new ArrayList<>(quizList);
                i.putExtra("quizList", list);
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
                Intent i = new Intent(MultipleChoiceActivity.this, MultipleChoiceSolutionActivity.class);
                i.putExtra("score", getScore());
                // Change List to ArrayList to accommodate subList
                ArrayList<Quiz> list = new ArrayList<>(quizList);
                i.putExtra("quizList", list);
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
            indexCurrentQuestion--;
            if (indexCurrentQuestion == 0) buttonPrevious.setEnabled(false);
            if (indexCurrentQuestion != (quizList.size() - 1)) buttonNext.setEnabled(true);
            Quiz currentQuestion = quizList.get(indexCurrentQuestion);
            currentQuestionView(currentQuestion);

            radioGroup = findViewById(R.id.celebrityOption);
            if (currentQuestion.userAnswer == 0) radioGroup.clearCheck();
            else {
                switch (currentQuestion.userAnswer) {
                    case 1: {
                        radioGroup.check(R.id.radioButtonOne);
                        break;
                    }
                    case 2: {
                        radioGroup.check(R.id.radioButtonTwo);
                        break;
                    }
                    case 3: {
                        radioGroup.check(R.id.radioButtonThree);
                        break;
                    }
                    case 4: {
                        radioGroup.check(R.id.radioButtonFour);
                        break;
                    }
                }
            }
        }
    }

    // Pre-define new views before setting next question as current question, for index > list.size()
    public void onButtonNext(View view) {
        if (indexCurrentQuestion != (quizList.size() - 1)) {
            Quiz currentQuestion = quizList.get(indexCurrentQuestion);
            currentQuestionView(currentQuestion);

            if (currentQuestion.userAnswer == currentQuestion.correctAnswer) {
                indexCurrentQuestion++;
                if (indexCurrentQuestion == (quizList.size() - 1)) buttonNext.setEnabled(false);
                if (indexCurrentQuestion != 0) buttonPrevious.setEnabled(true);
                currentQuestion = quizList.get(indexCurrentQuestion);
                currentQuestionView(currentQuestion);

                radioGroup = findViewById(R.id.celebrityOption);
                if (currentQuestion.userAnswer == 0) radioGroup.clearCheck();
                else {
                    switch (currentQuestion.userAnswer) {
                        case 1: {
                            radioGroup.check(R.id.radioButtonOne);
                            break;
                        }
                        case 2: {
                            radioGroup.check(R.id.radioButtonTwo);
                            break;
                        }
                        case 3: {
                            radioGroup.check(R.id.radioButtonThree);
                            break;
                        }
                        case 4: {
                            radioGroup.check(R.id.radioButtonFour);
                            break;
                        }
                    }
                }
            } else {
                heartCounter();
            }
        }
    }

    private void heartCounter() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        switch (this.heartCnt) {
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

        if (heartCnt == 3) {
            setRankingDB();
//            setIncorrectNoteDB();
            stopTimer();
            Intent i = new Intent(MultipleChoiceActivity.this, MultipleChoiceSolutionActivity.class);
            i.putExtra("score", getScore());
            // Change List to ArrayList to accommodate subList
            ArrayList<Quiz> list = new ArrayList<>(quizList);
            i.putExtra("quizList", list);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void currentQuestionView(Quiz currentQuestion) {
        questionView.setText(String.format("%s. %s", indexCurrentQuestion + 1, currentQuestion.question));
        radioButtonOne.setText(currentQuestion.one);
        radioButtonTwo.setText(currentQuestion.two);
        radioButtonThree.setText(currentQuestion.three);
        radioButtonFour.setText(currentQuestion.four);
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
