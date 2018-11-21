package com.udacity.quiztime.ui.ui.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.udacity.quiztime.R;
import com.udacity.quiztime.data.Quiz;
import com.udacity.quiztime.ui.QuizActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PageFragment extends Fragment {
    private static final String ARG_PAGE = "ARG_PAGE";

    private Quiz mQuiz;
    private RadioGroup radioGroup;
    private View rootView;

    public static PageFragment newInstance(Quiz quiz) {

        Gson gson = new Gson();
        String quizJson = gson.toJson(quiz);

        Bundle args = new Bundle();

        args.putString(ARG_PAGE, quizJson);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String quiz = null;
        if (getArguments() != null) {
            quiz = getArguments().getString(ARG_PAGE);
        }

        Gson gson = new Gson();
        mQuiz = gson.fromJson(quiz, Quiz.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_page, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewsAndSetText();
    }

    private void initViewsAndSetText() {
        TextView question = rootView.findViewById(R.id.question);
        radioGroup = rootView.findViewById(R.id.radioGroup);
        RadioButton o1 = rootView.findViewById(R.id.o1);
        o1.setTag("1");
        RadioButton o2 = rootView.findViewById(R.id.o2);
        o2.setTag("2");
        RadioButton o3 = rootView.findViewById(R.id.o3);
        o3.setTag("3");
        RadioButton o4 = rootView.findViewById(R.id.o4);
        o4.setTag("4");


        question.setText(mQuiz.getQ());
        o1.setText(mQuiz.getO1());
        o2.setText(mQuiz.getO2());
        o3.setText(mQuiz.getO3());
        o4.setText(mQuiz.getO4());

        setRadioButtonOnClickListener();
    }

    private void setRadioButtonOnClickListener() {

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            String mySelectedIndex = radioButton.getTag().toString();
            String mAnswer = mQuiz.getC();
            switch (mAnswer) {
                case "A":
                    mAnswer = "1";
                    break;
                case "B":
                    mAnswer = "2";
                    break;
                case "C":
                    mAnswer = "3";
                    break;
                case "D":
                    mAnswer = "4";
                    break;
                default:
                    break;

            }


            Log.d("PF", mySelectedIndex + " Index and correct is " + mAnswer);

            if (mySelectedIndex.equals(mAnswer)) {

                ((QuizActivity) requireActivity()).AddCorrectScore();

                radioButton.setTextColor(getResources().getColor(R.color.correct_answer));
            } else {

                ((QuizActivity) requireActivity()).AddWrongScore();

                radioButton.setTextColor(getResources().getColor(R.color.wrong_answer));
            }

            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                String tag = radioGroup.getChildAt(i).getTag().toString();
                if (tag.equals(mAnswer)) {
                    ((RadioButton) radioGroup.getChildAt(i)).setTextColor(getResources().getColor(R.color.correct_answer));

                }
                radioGroup.getChildAt(i).setEnabled(false);
            }

        });


        radioGroup.setEnabled(false);
    }

}
