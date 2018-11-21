package com.udacity.quiztime.ui.list;



import com.udacity.quiztime.data.network.QuizDataSourceFactory;
import com.udacity.quiztime.models.QuizList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

class TrendingFragmentViewModel extends ViewModel {
    private final LiveData<PagedList<QuizList>> mQuizList;


    TrendingFragmentViewModel(int mParam) {
        Executor executor = Executors.newFixedThreadPool(5);
        QuizDataSourceFactory factory = new QuizDataSourceFactory(executor, mParam);


        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build();

        //noinspection unchecked
        mQuizList = (new LivePagedListBuilder<Long, QuizList>(factory, pageConfig)).setFetchExecutor(executor)
//                .setBackgroundThreadExecutor(executor)
                .build();


    }


    LiveData<PagedList<QuizList>> getmQuizList() {
        return mQuizList;
    }

}
