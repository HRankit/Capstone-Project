package com.udacity.quiztime.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Quiz {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("o1")
    @Expose
    private String o1;
    @SerializedName("o2")
    @Expose
    private String o2;
    @SerializedName("o3")
    @Expose
    private String o3;
    @SerializedName("o4")
    @Expose
    private String o4;
    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("quizid")
    @Expose
    private String quizid;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO4() {
        return o4;
    }

    public void setO4(String o4) {
        this.o4 = o4;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

}