package com.udacity.quiztime.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.recyclerview.widget.DiffUtil;

public class QuizList {


    public static final DiffUtil.ItemCallback<QuizList> DIFF_CALL = new DiffUtil.ItemCallback<QuizList>() {
        @Override
        public boolean areItemsTheSame(QuizList oldItem, QuizList newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(QuizList oldItem, QuizList newItem) {
            return oldItem.id == newItem.id;
        }
    };


    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("noofq")
    @Expose
    private Long noofq;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("id")
    @Expose
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNoofq() {
        return noofq;
    }

    public void setNoofq(Long noofq) {
        this.noofq = noofq;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
