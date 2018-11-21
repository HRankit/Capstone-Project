package com.udacity.quiztime.adapters;

import android.content.Context;

import com.udacity.quiztime.data.Quiz;
import com.udacity.quiztime.ui.ui.quiz.PageFragment;
import com.udacity.quiztime.ui.ui.quiz.ResultsFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Quiz> quizzes;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context, List<Quiz> quizzes) {
        super(fm);
        this.quizzes = quizzes;
    }

    @Override
    public int getCount() {
        return quizzes.size() + 1;
    }

    @Override
    public Fragment getItem(int position) {
        if (isItLastPosition(position)) {
            return ResultsFragment.newInstance();
        } else {
            return PageFragment.newInstance(quizzes.get(position));
        }
    }

    private boolean isItLastPosition(int position) {
        return position == quizzes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (isItLastPosition(position)) {
            return "Results";
        } else {
            // Generate title based on item position
            return String.valueOf(position + 1);
        }


    }
}
