package com.udacity.quiztime.ui.list;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.udacity.quiztime.R;
import com.udacity.quiztime.adapters.SavedQuizListAdapter;
import com.udacity.quiztime.data.database.QuizListEntry;
import com.udacity.quiztime.ui.QuizActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.udacity.quiztime.ui.Main2Activity.SHOW_QUIZ;
import static com.udacity.quiztime.ui.Main2Activity.SHOW_WHAT;
import static com.udacity.quiztime.ui.QuizActivity.QUIZ_AUTHOR;
import static com.udacity.quiztime.ui.QuizActivity.QUIZ_ID;
import static com.udacity.quiztime.ui.ui.quiz.QuizFragment.provideMainActivityViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment implements SavedQuizListAdapter.SavedItemClickListener {


    private View rootView;
    private RecyclerView mRecyclerView;
    private SavedQuizListAdapter.SavedItemClickListener listener;
    private TextView placeHolder_tv;

    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_saved, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = rootView.findViewById(R.id.listQuizRV);

        placeHolder_tv = rootView.findViewById(R.id.placeHolder_tv);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        listener = this;

        SavedFragmentViewModelFactory factory = provideMainActivityViewModelFactory(requireActivity().getApplicationContext(), "");

        SavedFragmentViewModel allSavedQuiz = ViewModelProviders.of(this, factory).get(SavedFragmentViewModel.class);
        allSavedQuiz.getAllSavedQuiz().observe(this, QuizListEntry -> {

            if (QuizListEntry.size() == 0) {
                placeHolder_tv.setText(R.string.no_quiz_saved_yet);
                placeHolder_tv.setVisibility(View.VISIBLE);
            } else {
                SavedQuizListAdapter adapter1 = new SavedQuizListAdapter(requireActivity(),
                        listener, QuizListEntry);

                SharedPreferences sharedpreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                Gson gson = new Gson();
                String widgetData = gson.toJson(QuizListEntry);

                editor.putString("widgetdetails", widgetData);
                editor.apply();


                mRecyclerView.setAdapter(adapter1);
                placeHolder_tv.setVisibility(View.GONE);

            }


        });
    }


    @Override
    public void onSavedItemClick(QuizListEntry quizList) {
        Intent myIntent = new Intent(requireActivity(), QuizActivity.class);
        myIntent.putExtra(QUIZ_ID, quizList.getId());
        myIntent.putExtra(QUIZ_AUTHOR, quizList.getAuthor());
        myIntent.putExtra(SHOW_WHAT, SHOW_QUIZ);

        startActivity(myIntent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    public void onClick(View v) {

    }
}
