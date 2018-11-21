/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.quiztime.data.network;


import android.content.Context;

import com.udacity.quiztime.data.database.QuizEntry;
import com.udacity.quiztime.utils.AppExecutors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Provides an API for doing all operations with the server data
 */
public class QuizDataSource {
    // The number of days we want our API to return, set to 14 days or two weeks

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static QuizDataSource sInstance;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<QuizEntry[]> mDownloadedWeatherForecasts;

    private QuizDataSource(Context context, AppExecutors executors) {
        mDownloadedWeatherForecasts = new MutableLiveData<>();
    }

    /**
     * Get the singleton for this class
     */
    public static QuizDataSource getInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new QuizDataSource(context.getApplicationContext(), executors);
            }
        }
        return sInstance;
    }

    public LiveData<QuizEntry[]> getCurrentQuiz() {
        return mDownloadedWeatherForecasts;
    }


}