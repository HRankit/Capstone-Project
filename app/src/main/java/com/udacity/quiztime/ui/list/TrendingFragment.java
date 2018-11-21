package com.udacity.quiztime.ui.list;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.quiztime.R;
import com.udacity.quiztime.adapters.QuizListAdapter;
import com.udacity.quiztime.data.database.AppDatabase;
import com.udacity.quiztime.data.database.QuizListEntry;
import com.udacity.quiztime.models.QuizList;
import com.udacity.quiztime.ui.QuizActivity;
import com.udacity.quiztime.utils.AppExecutors;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends Fragment implements QuizListAdapter.CustomItemClickListener {


    private View rootView;
    private AppDatabase mDb;
    private QuizListAdapter adapter;

    public TrendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_trending, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDb = AppDatabase.getInstance(requireActivity().getApplicationContext());

        RecyclerView mRecyclerView = rootView.findViewById(R.id.listQuizRV);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new QuizListAdapter(requireActivity(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        TrendingFragmentViewModel mMoviesViewModel = ViewModelProviders.of(this, new TrendingFragmentViewModelFactory(0)).get(TrendingFragmentViewModel.class);
        mMoviesViewModel.getmQuizList().observe(this, movies -> {
            Log.d("MA", String.valueOf(movies.size()));

            adapter.submitList(movies);
        });
    }

    @Override
    public void onItemClick(QuizList quizList) {
        QuizListEntry quizListEntry = new QuizListEntry(quizList.getTitle(),
                quizList.getNoofq(),
                quizList.getAuthor(),
                quizList.getImg(),
                quizList.getThumb(),
                quizList.getId());
        AppExecutors.getInstance().diskIO().execute(() -> mDb.quizListDao().bulkInsert(quizListEntry));

        Intent myIntent = new Intent(requireActivity(), QuizActivity.class);
        myIntent.putExtra(QUIZ_ID, quizList.getId());
        myIntent.putExtra(QUIZ_AUTHOR, quizList.getAuthor());
        myIntent.putExtra(SHOW_WHAT, SHOW_QUIZ);

        startActivity(myIntent);
        requireActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    @Override
    public void onClick(View v) {

    }
}
