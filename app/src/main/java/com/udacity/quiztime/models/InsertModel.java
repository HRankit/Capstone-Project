package com.udacity.quiztime.models;

public class InsertModel {


    private String titleStr;
    private String questionStr;
    private String ansStr;
    private String o1Str;
    private String o2Str;
    private String o3Str;
    private String o4Str;
    private String userID;

    @SuppressWarnings("unused")
    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    public String getAnsStr() {
        return ansStr;
    }

    public void setAnsStr(String ansStr) {
        this.ansStr = ansStr;
    }

    public String getO1Str() {
        return o1Str;
    }

    public void setO1Str(String o1Str) {
        this.o1Str = o1Str;
    }

    public String getO2Str() {
        return o2Str;
    }

    public void setO2Str(String o2Str) {
        this.o2Str = o2Str;
    }

    public String getO3Str() {
        return o3Str;
    }

    public void setO3Str(String o3Str) {
        this.o3Str = o3Str;
    }

    public String getO4Str() {
        return o4Str;
    }

    public void setO4Str(String o4Str) {
        this.o4Str = o4Str;
    }

    @SuppressWarnings("unused")
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}