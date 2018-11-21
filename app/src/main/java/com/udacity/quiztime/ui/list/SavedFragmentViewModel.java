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

package com.udacity.quiztime.ui.list;


import com.udacity.quiztime.data.DataRepository;
import com.udacity.quiztime.data.database.QuizListEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 *
 */
class SavedFragmentViewModel extends ViewModel {

    private final LiveData<List<QuizListEntry>> mForecast;

    SavedFragmentViewModel(DataRepository repository, String mParam) {
        mForecast = repository.getAllQuizList();
    }

    LiveData<List<QuizListEntry>> getAllSavedQuiz() {
        return mForecast;
    }


}
