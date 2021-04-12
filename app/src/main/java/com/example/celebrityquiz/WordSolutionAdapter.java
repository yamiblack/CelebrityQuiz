package com.example.celebrityquiz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WordSolutionAdapter extends RecyclerView.Adapter {
    private List<Quiz> quizList;
    private List<String> userAnswerText;
    private List<String> correctAnswer;
    private Context context;

    // Constructor to initialize all arrayList
    WordSolutionAdapter(List<Quiz> quizList, Context context, List<String> userAnswerText, List<String> correctAnswer) {
        this.quizList = quizList;
        this.userAnswerText = userAnswerText;
        this.context = context;
        this.correctAnswer = correctAnswer;
    }

    @NonNull
    @Override
    // Build view layout and call ViewHolder, QuizHolder class
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutInflater = LayoutInflater.from(context).
                inflate(R.layout.wordsolution, viewGroup, false);
        return new RecyclerView.ViewHolder(layoutInflater) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        // Define recycler views
        TextView viewQuestion = viewHolder.itemView.findViewById(R.id.celebrityQuestion);
        TextView viewCorrectAnswer = viewHolder.itemView.findViewById(R.id.correctAnswer);
        TextView viewUserAnswer = viewHolder.itemView.findViewById(R.id.userAnswer);
        ImageView imageView = viewHolder.itemView.findViewById(R.id.celebrityImage);

        viewHolder.itemView.findViewById(R.id.horizontalDivider);

        // Format recycler view content
        if (!quizList.isEmpty()) {
            Quiz quiz = quizList.get(position);
            String correctAns = correctAnswer.get(position);
            String userAnswer = "";

            if (!(userAnswerText.isEmpty()))
                if (position < userAnswerText.size())
                    userAnswer = userAnswerText.get(position);

            viewQuestion.setText(String.format("%s. %s", position + 1, quiz.question));
            viewUserAnswer.setText(userAnswer);
            viewCorrectAnswer.setText(correctAns);
            Glide.with(imageView.getContext()).load(quiz.imageUrl).into(imageView);

            // This is crucial for Marking system
            /* First, determine if userAnswer is the same as correctAnswer, IF YES, mark it
             * green and set it checked. ELSE, if user didn't select anything clearCheck() else if
             * userAnswer is wrong, mark userAnswer red, locate
             * correctAnswer and mark it green.
             */
            viewCorrectAnswer.setTextColor(Color.parseColor("#FF0BA512"));
            if (quiz.userAnswer == quiz.correctAnswer) {
                viewUserAnswer.setTextColor(Color.parseColor("#FF0BA512"));

            } else {
                viewUserAnswer.setTextColor(Color.RED);
            }
        }
    }

    // Default ViewHolder methods
    @Override
    public int getItemCount() {
        if (quizList == null) return 0;
        return quizList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
