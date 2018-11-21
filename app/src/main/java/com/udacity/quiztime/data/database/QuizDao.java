package com.udacity.quiztime.data.database;

import com.udacity.quiztime.data.Quiz;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface QuizDao {


    @Query("SELECT *  FROM quiz WHERE quizid = :quizid ORDER BY id")
    LiveData<List<Quiz>> getQuizByQuizID(String quizid);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(QuizEntry... quizEntries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsertList(List<QuizEntry> quizEntries);

    @Query("DELETE FROM quiz WHERE quizid = :quizid")
    void deleteThisQuiz(String quizid);

//    @Query("DELETE FROM quiz")
//    void deleteOldWeather();

}
