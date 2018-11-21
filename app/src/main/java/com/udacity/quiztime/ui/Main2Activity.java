package com.udacity.quiztime.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.udacity.quiztime.R;
import com.udacity.quiztime.adapters.MainActPagerAdapter;
import com.udacity.quiztime.service.MyIntentService;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;


@SuppressWarnings("ALL")
public class Main2Activity extends AppCompatActivity {

    public static final int SHOW_QUIZ = 1;
    public static final String FILTER_ACTION_KEY = "any_key";
    public static String SHOW_WHAT = "showWhat";
    public static int INSERT_QUIZ = 2;
    private int RC_SIGN_IN = 9999;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseAuth.getInstance().signOut();

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Trending"));
        tabLayout.addTab(tabLayout.newTab().setText("Saved"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final MainActPagerAdapter adapter = new MainActPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void fabClicked(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
//            userSignedIn(user);
            goToInsertQuiz();

        } else {
            // No user is signed in
            initSignIn();
        }


    }

    private void userSignedIn(FirebaseUser user) {

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        // Check if user's email is verified
        boolean emailVerified = user.isEmailVerified();

        String uid = user.getUid();

        Log.d("M2A", "Name " + name + "" +
                " email" + email + "" +
                " photoURL " + photoUrl + "" +
                " Email Verified " + emailVerified +
                " uid " + uid);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("name", name);
        editor.putString("email", email);
        if (photoUrl != null) {
            editor.putString("photoURL", photoUrl.toString());
        }
        editor.putBoolean("e_verified", emailVerified);
        editor.putString("uid", uid);
        editor.apply();

        if (photoUrl != null) {

            Intent intent = new Intent(Main2Activity.this, MyIntentService.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("photoURL", photoUrl.toString());
            intent.putExtra("e_verified", emailVerified);
            intent.putExtra("uid", uid);

            startService(intent);


//            sendUserDataToServer(name, email, photoUrl.toString(), emailVerified, uid);
        }


        goToInsertQuiz();


    }


    private void setReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        setReceiver();
        super.onStart();
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onStop();
    }

    private void goToInsertQuiz() {
        Intent myIntent = new Intent(this, QuizActivity.class);
        myIntent.putExtra(SHOW_WHAT, INSERT_QUIZ);

        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    private void initSignIn() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.mipmap.ic_launcher)
                        .setTosAndPrivacyPolicyUrls("https://comsec.co.in/dentalpockets/tnc/tnc.html",
                                "https://comsec.co.in/dentalpockets/tnc/pp.html")
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    userSignedIn(user);
                }
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Log.d("MainAct", "User Canceled Sign In");
            }
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("broadcastMessage");
            Toast.makeText(Main2Activity.this, R.string.success_message, Toast.LENGTH_LONG).show();
        }
    }
}
