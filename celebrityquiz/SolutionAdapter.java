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

public class SolutionAdapter extends RecyclerView.Adapter {
    private List<Quiz> quizList;
    private Context context;

    // Constructor to initialize all arrayList
    SolutionAdapter(List<Quiz> quizList, Context context) {
        this.quizList = quizList;
        this.context = context;
    }

    @NonNull
    @Override
    // Build view layout and call ViewHolder, QuizHolder class
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutInflater = LayoutInflater.from(context).
                inflate(R.layout.solution, viewGroup, false);
        return new RecyclerView.ViewHolder(layoutInflater) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        // Define recycler views
        TextView viewQuestion = viewHolder.itemView.findViewById(R.id.celebrityQuestion);
        ImageView imageView = viewHolder.itemView.findViewById(R.id.celebrityImage);
        RadioGroup radioGroup = viewHolder.itemView.findViewById(R.id.celebrityOption);
        RadioButton radioButtonOne = viewHolder.itemView.findViewById(R.id.radioButtonOne);
        RadioButton radioButtonTwo = viewHolder.itemView.findViewById(R.id.radioButtonTwo);
        RadioButton radioButtonThree = viewHolder.itemView.findViewById(R.id.radioButtonThree);
        RadioButton radioButtonFour = viewHolder.itemView.findViewById(R.id.radioButtonFour);
        viewHolder.itemView.findViewById(R.id.horizontalDivider);

        // Format recycler view content
        if(!quizList.isEmpty()) {
            Quiz quiz = quizList.get(position);

            viewQuestion.setText(String.format("%s. %s", position + 1, quiz.question));
            Glide.with(imageView.getContext()).load(quiz.imageUrl).into(imageView);
            radioButtonOne.setText(quiz.one);
            radioButtonTwo.setText(quiz.two);
            radioButtonThree.setText(quiz.three);
            radioButtonFour.setText(quiz.four);

            // This is crucial for Marking system
            /* First, determine if userAnswer is the same as correctAnswer, IF YES, mark it
            * green and set it checked. ELSE, if user didn't select anything clearCheck() else if
            * userAnswer is wrong, mark userAnswer red, locate
            * correctAnswer and mark it green.
            */
            if(quiz.userAnswer == quiz.correctAnswer) {
                if(1 == quiz.correctAnswer) {
                    radioButtonOne.setChecked(true);
                    radioButtonOne.setTextColor(Color.parseColor("#FF0BA512"));
                }
                else if (2 == quiz.correctAnswer) {
                    radioButtonTwo.setChecked(true);
                    radioButtonTwo.setTextColor(Color.parseColor("#FF0BA512"));
                }
                else if (3 == quiz.correctAnswer) {
                    radioButtonThree.setChecked(true);
                    radioButtonThree.setTextColor(Color.parseColor("#FF0BA512"));
                }
                else if (4 == quiz.correctAnswer) {
                    radioButtonFour.setChecked(true);
                    radioButtonFour.setTextColor(Color.parseColor("#FF0BA512"));
                }
            }
            else {
                if(1 == quiz.userAnswer) {
                    radioButtonOne.setChecked(true);
                    radioButtonOne.setTextColor(Color.RED);
                }
                if(1 == quiz.correctAnswer) {
                    radioButtonOne.setTextColor(Color.parseColor("#FF0BA512"));
                }

                if(2 == quiz.userAnswer) {
                    radioButtonTwo.setChecked(true);
                    radioButtonTwo.setTextColor(Color.RED);
                }
                if(2 == quiz.correctAnswer) {
                    radioButtonTwo.setTextColor(Color.parseColor("#FF0BA512"));
                }

                if(3 == quiz.userAnswer) {
                    radioButtonThree.setChecked(true);
                    radioButtonThree.setTextColor(Color.RED);
                }
                if(3 == quiz.correctAnswer) {
                    radioButtonThree.setTextColor(Color.parseColor("#FF0BA512"));
                }

                if(4 == quiz.userAnswer) {
                    radioButtonFour.setChecked(true);
                    radioButtonFour.setTextColor(Color.RED);
                }
                if(4 == quiz.correctAnswer) {
                    radioButtonFour.setTextColor(Color.parseColor("#FF0BA512"));
                }
            }
            if(0 == quiz.userAnswer) radioGroup.clearCheck();

            // Disable all radioButton to avoid answer misinterpretations
            radioButtonOne.setEnabled(false);
            radioButtonTwo.setEnabled(false);
            radioButtonThree.setEnabled(false);
            radioButtonFour.setEnabled(false);
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
