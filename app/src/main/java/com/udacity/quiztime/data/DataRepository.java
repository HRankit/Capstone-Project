package com.udacity.quiztime.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.quiztime.data.database.QuizDao;
import com.udacity.quiztime.data.database.QuizEntry;
import com.udacity.quiztime.data.database.QuizListDao;
import com.udacity.quiztime.data.database.QuizListEntry;
import com.udacity.quiztime.data.network.QuizApi;
import com.udacity.quiztime.data.network.QuizApiFactory;
import com.udacity.quiztime.data.network.QuizDataSource;
import com.udacity.quiztime.ui.ui.quiz.QuizResult;
import com.udacity.quiztime.utils.AppExecutors;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static final String LOG_TAG = DataRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static DataRepository sInstance;
    private final QuizDao quizDao;
    private final AppExecutors mExecutors;
    private final LiveData<List<QuizListEntry>> mAllQuizList;

    private DataRepository(QuizDao weatherDao, QuizListDao quizListDaoss,
                           QuizDataSource weatherNetworkDataSource,
                           AppExecutors executors) {
        quizDao = weatherDao;
        mExecutors = executors;

        mAllQuizList = quizListDaoss.getAllQuiz();

        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        LiveData<QuizEntry[]> networkData = weatherNetworkDataSource.getCurrentQuiz();
        networkData.observeForever((QuizEntry[] quizData) -> mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Old weather deleted");

                quizDao.bulkInsert(quizData);
                Log.d(LOG_TAG, "New values inserted");
            }
        }));
    }

    public synchronized static DataRepository getInstance(
            QuizDao quizDao, QuizListDao quizListDao, QuizDataSource weatherNetworkDataSource,
            AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new DataRepository(quizDao, quizListDao, weatherNetworkDataSource,
                        executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    private synchronized void initializeData(String quizID) {

        QuizApi quizWebService;
        quizWebService = QuizApiFactory.create();
        Call<QuizResult> call = quizWebService.fetchThisQuiz(quizID);

        call.enqueue(new Callback<QuizResult>() {
            @Override
            public void onResponse(@NonNull Call<QuizResult> call, @NonNull Response<QuizResult> response) {
                List<Quiz> quizList = null;
                if (response.body() != null) {
                    quizList = response.body().getQuiz();
                }
                if (quizList != null) {

                    Gson gson = new Gson();
                    String quiz = gson.toJson(quizList);


                    Type listType = new TypeToken<ArrayList<QuizEntry>>() {
                    }.getType();
                    List<QuizEntry> quizEntry = new Gson().fromJson(quiz, listType);


                    mExecutors.diskIO().execute(() -> {
                        quizDao.deleteThisQuiz(quizEntry.get(0).getQuizid());
                        quizDao.bulkInsertList(quizEntry);

                    });

                }

            }

            @Override
            public void onFailure(@NonNull Call<QuizResult> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Log.e("DataRepository", "Getting quiz failed. " + t.getMessage());
                } else {
                    Log.e("DataRepository", "Getting quiz failed. " + t.getMessage());
                }

            }

        });

    }

    /**
     * Database related operations
     **/

    public LiveData<List<Quiz>> getThisQuiz(String QuizID) {
        initializeData(QuizID);
        return quizDao.getQuizByQuizID(QuizID);
    }

    public LiveData<List<QuizListEntry>> getAllQuizList() {
        return mAllQuizList;
    }


}
