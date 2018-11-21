package com.udacity.quiztime.data.database;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz", indices = {@Index(value = "q", unique = true)})
public class QuizEntry {
    private final String q;
    private final String o1;
    private final String o2;
    private final String o3;
    private final String o4;
    private final String c;
    private final String id;
    private final String quizid;
    @PrimaryKey(autoGenerate = true)
    private int primaryid;

    @Ignore
    public QuizEntry(String q, String o1, String o2,
                     String o3, String o4, String c,
                     String id, String quizid) {
        this.q = q;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
        this.c = c;
        this.id = id;
        this.quizid = quizid;

    }

    public QuizEntry(int primaryid,
                     String q, String o1, String o2,
                     String o3, String o4, String c,
                     String id, String quizid) {
        this.primaryid = primaryid;
        this.q = q;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
        this.c = c;
        this.id = id;
        this.quizid = quizid;

    }


    int getPrimaryid() {
        return primaryid;
    }

    String getQ() {
        return q;
    }

    public String getO1() {
        return o1;
    }

    public String getO2() {
        return o2;
    }

    public String getO3() {
        return o3;
    }

    public String getO4() {
        return o4;
    }

    public String getC() {
        return c;
    }


    public String getId() {
        return id;
    }

    public String getQuizid() {
        return quizid;
    }
}
