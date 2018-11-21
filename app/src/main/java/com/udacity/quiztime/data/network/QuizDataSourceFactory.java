package com.udacity.quiztime.data.network;


import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class QuizDataSourceFactory extends DataSource.Factory {
    private final MutableLiveData<QuizListDataSource> mutableLiveData;
    private final Executor executor;
    private final int mParam;


    public QuizDataSourceFactory(Executor executor, int integer) {
        this.executor = executor;
        mutableLiveData = new MutableLiveData<>();
        this.mParam = integer;
    }

    @NonNull
    @Override
    public DataSource create() {

        QuizListDataSource moviesDataSource = new QuizListDataSource(executor, mParam);
        mutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;
    }

    public MutableLiveData<QuizListDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}