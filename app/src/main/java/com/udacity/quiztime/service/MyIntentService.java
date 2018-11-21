package com.udacity.quiztime.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.udacity.quiztime.data.network.QuizApi;
import com.udacity.quiztime.data.network.QuizApiFactory;
import com.udacity.quiztime.ui.Main2Activity;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyIntentService extends IntentService {


    private Intent mIntent;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mIntent = intent;
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String photoURL = intent.getStringExtra("photoURL");
        Boolean e_verified = intent.getBooleanExtra("e_verified", true);
        String uid = intent.getStringExtra("uid");


        sendUserDataToServer(name, email, photoURL, e_verified, uid);


    }


    private void sendUserDataToServer(String name, String email, String photourl, boolean emailVerified, String uid) {
        QuizApi quizWebService;
        quizWebService = QuizApiFactory.create();

        Call<ResponseBody> call = quizWebService.sendUserData(name, email, photourl, emailVerified, uid);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        String res = response.body().string();
                        Log.d("MACT", "Response Body is " + res);
                        sendBackIntent("You have been registered on our server.");

                    } else {
                        Log.d("MACT", "Response Body is null");
                        sendBackIntent("Some error occurred while registering you onto the server.");

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Log.e("MainAct", "Request Cancelled by User. " + t.getMessage());
                    sendBackIntent("Request Cancelled by User. ");

                } else {
                    Log.e("MainAct", "other larger issue, i.e. no network connection? " + t.getMessage());
                    sendBackIntent("Some error occurred. May be a network issue.");

                }
            }

        });

    }

    private void sendBackIntent(String s) {
        mIntent.setAction(Main2Activity.FILTER_ACTION_KEY);
        String echoMessage;
        echoMessage = s;
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(mIntent.putExtra("broadcastMessage", echoMessage));
    }

}
