package com.udacity.quiztime.data.network;

import com.udacity.quiztime.models.QuizListResults;
import com.udacity.quiztime.ui.ui.quiz.QuizResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuizApi {
    @GET("/quiz/")
    Call<QuizListResults> fetchAllQuiz(@Query("q") String q,
                                       @Query("page") long page);

    @GET("/quiz/")
    Call<QuizResult> fetchThisQuiz(@Query("t") String quizID);


    @POST("/quiz/index2.php")
    @FormUrlEncoded
    Call<ResponseBody> sendJson(@Field("title") String quizID);

    @POST("/quiz/loguser.php")
    @FormUrlEncoded
    Call<ResponseBody> sendUserData(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("photourl") String photourl,
                                    @Field("emailVerified") boolean emailVerified,
                                    @Field("uid") String uid);
}
