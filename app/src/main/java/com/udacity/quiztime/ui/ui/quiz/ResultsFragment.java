package com.udacity.quiztime.ui.ui.quiz;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.udacity.quiztime.R;
import com.udacity.quiztime.ui.QuizActivity;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    private static final String ARG_PAGE = "ARG_PAGE";
    private TextView resultTextView;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();

        args.putString(ARG_PAGE, "1");
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);
        resultTextView = rootView.findViewById(R.id.final_result);
        resultTextView.setVisibility(View.GONE);
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }

        setScore();
    }

    private void setScore() {
        int CorrectScore = ((QuizActivity) requireActivity()).GetCorrectScore();
        int WrongScore = ((QuizActivity) requireActivity()).GetWrongScore();
        Log.d("QUIZACT", "CS: " + CorrectScore + " WS:" + WrongScore);
        int FinalScore = CorrectScore + WrongScore;
        resultTextView.setText(String.format(Locale.getDefault(), "%d/%d", CorrectScore, FinalScore));
        resultTextView.setVisibility(View.VISIBLE);


        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                resultTextView.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(1500);
        animate.setFillAfter(true);
        resultTextView.startAnimation(animate);
    }


}
