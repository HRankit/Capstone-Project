package com.udacity.quiztime.ui.list;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrendingFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int mParam;


    TrendingFragmentViewModelFactory(int param) {
        mParam = param;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new TrendingFragmentViewModel(mParam);
    }

}
