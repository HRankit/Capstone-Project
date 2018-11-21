package com.udacity.quiztime.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.udacity.quiztime.R;
import com.udacity.quiztime.ui.insert.InsertFragment;
import com.udacity.quiztime.ui.ui.quiz.QuizFragment;

import androidx.appcompat.app.AppCompatActivity;

import static com.udacity.quiztime.ui.Main2Activity.INSERT_QUIZ;
import static com.udacity.quiztime.ui.Main2Activity.SHOW_QUIZ;
import static com.udacity.quiztime.ui.Main2Activity.SHOW_WHAT;

public class QuizActivity extends AppCompatActivity {

    public static final String QUIZ_AUTHOR = "author";
    public static final String QUIZ_ID = "quiz_id";
    private int CorrectScore = 0;
    private int WrongScore = 0;
    private String CS = "CS";
    private String WS = "WS";
    private boolean freezeScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        initDeepLink();

        if (savedInstanceState == null) {

            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().getInt(SHOW_WHAT) == SHOW_QUIZ) {
                    String quizID = getIntent().getExtras().getString(QUIZ_ID);
                    showPageFragment(quizID);
                } else if (getIntent().getExtras().getInt(SHOW_WHAT) == INSERT_QUIZ) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, InsertFragment.newInstance())
                            .commitNow();
                }
            }
        } else {
            CorrectScore = savedInstanceState.getInt(CS);
            WrongScore = savedInstanceState.getInt(WS);
        }
    }

    private void initDeepLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                        Log.d("DEEPLINK", "This is the DeepLink" + deepLink);

                        String quizID = deepLink.toString().replace("https://comsec.co.in/quiz/?t=", "");
                        Log.d("DEEPLINK", "This is the Final QuizID" + quizID);

                        showPageFragment(quizID);
                    }


                    // Handle the deep link. For example, open the linked
                    // content, or apply promotional credit to the user's
                    // account.
                    // ...

                    // ...
                })
                .addOnFailureListener(this, e -> Log.w("DEEPLINK", "getDynamicLink:onFailure", e));
    }

    private void showPageFragment(String quizID) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, QuizFragment.newInstance(quizID))
                .commitNow();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(CS, CorrectScore);
        outState.putInt(WS, WrongScore);
    }

    public void AddCorrectScore() {
        if (!freezeScore) {
            CorrectScore++;
            QuizFragment fragment = (QuizFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            if (fragment != null) {
                fragment.changeTabColor(1);
            }
        }
        Log.d("QUIZACT", "Correct added. Coorrect sxore is " + CorrectScore);
    }

    public void AddWrongScore() {
        if (!freezeScore) {
            WrongScore++;
            QuizFragment fragment = (QuizFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            if (fragment != null) {
                fragment.changeTabColor(2);
            }
        }
        Log.d("QUIZACT", "rong added. rong sxore is " + WrongScore);

    }

    public int GetCorrectScore() {
        freezeScore = true;
        return CorrectScore;
    }

    public int GetWrongScore() {
        freezeScore = true;
        return WrongScore;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

    }
}
