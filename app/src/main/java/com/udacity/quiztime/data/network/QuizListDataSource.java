package com.udacity.quiztime.data.network;


import android.util.Log;

import com.udacity.quiztime.models.QuizList;
import com.udacity.quiztime.models.QuizListResults;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class QuizListDataSource extends PageKeyedDataSource<Long, QuizList> {
    private static final String TAG = "QuizListDataSource";
    private static final long DEFAULT_PAGE_NUMBER = 1;
    private final QuizApi quizWebService;
    private final MutableLiveData<NetworkState> networkState;
    private final MutableLiveData<NetworkState> initialLoading;
    private Call<QuizListResults> call;


    QuizListDataSource(Executor retryExecutor, int mParam) {

        quizWebService = QuizApiFactory.create();
        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }


    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }


    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, QuizList> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        call = quizWebService.fetchAllQuiz("", DEFAULT_PAGE_NUMBER);


        call.enqueue(new Callback<QuizListResults>() {
            @Override
            public void onResponse(@NonNull Call<QuizListResults> call, @NonNull Response<QuizListResults> response) {
                if (response.isSuccessful() && response.code() == 200) {

                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);
                    if (response.body() != null) {
                        callback.onResult(response.body().getQuizList(), null, (long) 2);
                    } else {
                        Log.e(TAG, "onResponse error " + response.message());
                    }

                } else {
                    Log.e(TAG, "onResponse error " + response.message());
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuizListResults> call, @NonNull Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: " + errorMessage);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }

        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, QuizList> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, QuizList> callback) {
        networkState.postValue(NetworkState.LOADING);

        call = quizWebService.fetchAllQuiz("", params.key);

        call.enqueue(new Callback<QuizListResults>() {
            @Override
            public void onResponse(@NonNull Call<QuizListResults> call, @NonNull Response<QuizListResults> response) {
                Long nextKey = null;

                if (response.isSuccessful() && response.code() == 200) {

                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);

                    if (response.body() != null) {
                        nextKey = (params.key == response.body().getTotalPages()) ? null : params.key + 1;
                    }

                    if (response.body() != null) {
                        callback.onResult(response.body().getQuizList(), nextKey);
                    }


                } else {
                    Log.e(TAG, "onResponse error " + response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuizListResults> call, @NonNull Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: " + errorMessage);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }


}

