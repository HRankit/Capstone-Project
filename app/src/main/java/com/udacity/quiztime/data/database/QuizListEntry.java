package com.udacity.quiztime.data.database;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "list", indices = {@Index(value = "id", unique = true)})
public class QuizListEntry {
    private final Long noofq;
    private final String author;
    @PrimaryKey(autoGenerate = true)
    private int primaryid;
    private String title;
    private String img;
    private String thumb;
    private String id;

    @Ignore
    public QuizListEntry(String title, Long noofq, String author, String img, String thumb,
                         String id) {
        this.title = title;
        this.noofq = noofq;
        this.author = author;
        this.img = img;
        this.thumb = thumb;
        this.id = id;

    }

    public QuizListEntry(int primaryid, String title, Long noofq, String author, String img, String thumb,
                         String id) {
        this.primaryid = primaryid;
        this.title = title;
        this.noofq = noofq;
        this.author = author;
        this.img = img;
        this.thumb = thumb;
        this.id = id;

    }


    int getPrimaryid() {
        return primaryid;
    }


    public String getTitle() {
        return title;
    }

    public Long getNoofq() {
        return noofq;
    }

    public String getAuthor() {
        return author;
    }

    String getImg() {
        return img;
    }

    public String getThumb() {
        return thumb;
    }

    public String getId() {
        return id;
    }
}
