package com.udacity.quiztime.ui.ui.quiz;

import com.udacity.quiztime.data.DataRepository;
import com.udacity.quiztime.data.Quiz;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

class QuizViewModel extends ViewModel {


    private final LiveData<List<Quiz>> getThisQuiz;


    QuizViewModel(DataRepository repository, String mParam) {
        getThisQuiz = repository.getThisQuiz(mParam);
    }

    LiveData<List<Quiz>> getThisQuiz() {
        return getThisQuiz;
    }
}
