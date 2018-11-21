package com.udacity.quiztime.ui.ui.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.quiztime.data.Quiz;

import java.util.List;

public class QuizResult {

    @SerializedName("quiz")
    @Expose
    private List<Quiz> quiz = null;
    @SerializedName("totalQuestions")
    @Expose
    private Long totalQuestions;

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

    public Long getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

}