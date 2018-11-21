package com.udacity.quiztime.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class QuizListResults {

    public static final DiffUtil.ItemCallback<QuizListResults> DIFF_CALL = new DiffUtil.ItemCallback<QuizListResults>() {
        @Override
        public boolean areItemsTheSame(QuizListResults oldItem, QuizListResults newItem) {
            return oldItem.page == newItem.page;
        }

        @Override
        public boolean areContentsTheSame(QuizListResults oldItem, QuizListResults newItem) {
            return oldItem.page == newItem.page;
        }
    };
    @SerializedName("quizList")
    @Expose
    private List<QuizList> quizList = null;
    @SerializedName("page")
    @Expose
    private Long page;
    @SerializedName("totalPages")
    @Expose
    private Long totalPages;

    public List<QuizList> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<QuizList> quizList) {
        this.quizList = quizList;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }


}