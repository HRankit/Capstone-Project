package com.udacity.quiztime.ui.list;


import android.util.Log;

import com.udacity.quiztime.data.network.QuizDataSourceFactory;
import com.udacity.quiztime.models.QuizList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

class TrendingFragmentViewModel extends ViewModel {
    private static final String TAG = "TheaterViewModel";
    private final LiveData<PagedList<QuizList>> mQuizList;


    TrendingFragmentViewModel(int mParam) {
        Log.d(TAG, "MoviesInTheaterViewModel: ");
        Executor executor = Executors.newFixedThreadPool(5);
        QuizDataSourceFactory factory = new QuizDataSourceFactory(executor, mParam);

//        LiveData<NetworkState> networkStateLiveData = Transformations.switchMap(factory.getMutableLiveData(), source -> {
//            Log.d(TAG, "apply: network change");
//            return source.getNetworkState();
//        });

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
        Log.d(TAG, "getmQuizList: ");
        return mQuizList;
    }

}
