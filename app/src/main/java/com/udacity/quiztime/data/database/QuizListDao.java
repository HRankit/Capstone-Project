package com.udacity.quiztime.data.database;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface QuizListDao {

    @Query("SELECT * FROM list")
    LiveData<List<QuizListEntry>> getAllQuiz();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(QuizListEntry... quizListEntries);



}
