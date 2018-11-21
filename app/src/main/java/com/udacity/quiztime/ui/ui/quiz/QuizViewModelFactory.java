package com.udacity.quiztime.ui.ui.quiz;

import com.udacity.quiztime.data.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class QuizViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataRepository mRepository;
    private final String mParam;

    QuizViewModelFactory(DataRepository repository, String param) {
        this.mRepository = repository;
        this.mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new QuizViewModel(mRepository, mParam);
    }
}