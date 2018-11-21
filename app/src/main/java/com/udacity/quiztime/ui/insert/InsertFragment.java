package com.udacity.quiztime.ui.insert;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.quiztime.R;
import com.udacity.quiztime.adapters.PreviewRVAdapter;
import com.udacity.quiztime.data.network.QuizApi;
import com.udacity.quiztime.data.network.QuizApiFactory;
import com.udacity.quiztime.models.InsertModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class InsertFragment extends Fragment {


    private final String PREVIEW_LIST = "preview_list";
    private View rootView;
    private TextInputLayout question, o1, o2, o3, o4, quiz_title;
    private String questionStr, ansStr, o1Str, o2Str, o3Str, o4Str, titleStr, shareURL;
    private List<InsertModel> insertModelList;
    private Spinner answer;
    private RecyclerView previewRV;
    private PreviewViewModel previewViewModel;
    private TextView titlePreview;

    public InsertFragment() {
        // Required empty public constructor
    }

    public static InsertFragment newInstance() {
        return new InsertFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_insert, container, false);


        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String listString = gson.toJson(insertModelList);
        outState.putString(PREVIEW_LIST, listString);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            Gson gson = new Gson();
            String listString = savedInstanceState.getString(PREVIEW_LIST);
            Type listType = new TypeToken<ArrayList<InsertModel>>() {
            }.getType();
            insertModelList = gson.fromJson(listString, listType);
        } else {
            insertModelList = new ArrayList<>();
        }

        question = rootView.findViewById(R.id.question);
        answer = rootView.findViewById(R.id.ans);
        o1 = rootView.findViewById(R.id.o1);
        o2 = rootView.findViewById(R.id.o2);
        o3 = rootView.findViewById(R.id.o3);
        o4 = rootView.findViewById(R.id.o4);
        quiz_title = rootView.findViewById(R.id.quiz_title);

        previewRV = rootView.findViewById(R.id.previewRV);
        titlePreview = rootView.findViewById(R.id.titlePreview);
        titlePreview.setText(R.string.preview);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        previewRV.setLayoutManager(layoutManager);

        previewRV.setHasFixedSize(true);


        previewViewModel =
                ViewModelProviders.of(this).get(PreviewViewModel.class);


        previewViewModel.getUserList().observe(this, data -> {
            if (data != null) {

                PreviewRVAdapter adapter1 = new PreviewRVAdapter(requireActivity(), data);
                previewRV.setAdapter(adapter1);

                ItemTouchHelper itemTouchHelper = new
                        ItemTouchHelper(new SwipeToDeleteCallback(adapter1));
                itemTouchHelper.attachToRecyclerView(previewRV);
//                if (data.size() == 0) {
//                } else {
//                }
            }


        });
        previewViewModel.getTitle().observe(this, data -> titlePreview.setText(String.format("PREVIEW - %s", data)));


        Button share = rootView.findViewById(R.id.share);
        share.setVisibility(View.GONE);
        share.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shareURL));
            startActivity(browserIntent);
        });


        Button add_another = rootView.findViewById(R.id.add_another);
        add_another.setOnClickListener(v -> {
            if (addToList()) {
                clearAllEditTexts();
            }
        });
    }

    private void publish() {
        if (!addToList()) {
            return;
        }

        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                getString(R.string.publish_in_progress), Snackbar.LENGTH_LONG)
                .show();


        QuizApi quizWebService;
        quizWebService = QuizApiFactory.create();


        Gson gson = new Gson();
        String json = gson.toJson(insertModelList);


        Call<ResponseBody> call = quizWebService.sendJson(json);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        String res = response.body().string();
                        Log.d("MACT", "Response Body is " + res);
                        sendToUser(res);
                    } else {
                        Log.d("MACT", "Response Body is null");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Log.e("InsertFrag", "Request Cancelled by User. " + t.getMessage());
                } else {
                    Log.e("InsertFrag", "Call Failed.other larger issue, i.e. no network connection? " + t.getMessage());
                }

            }

        });
    }

    private void sendToUser(String string) {
        Log.d("DEEPLINK", "String got to sendToUser is " + string);
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://comsec.co.in/quiz/?t=" + string))
                .setDomainUriPrefix("https://quiztime.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.udacity.quiztime")
                                .build())
                .buildDynamicLink();  // Or buildShortDynamicLink()


        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(dynamicLink.getUri())
                .buildShortDynamicLink()
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Short link created
                        Uri shortLink = task.getResult().getShortLink();
                        shareURL = shortLink.toString();
                        Uri flowchartLink = task.getResult().getPreviewLink();
                        Log.d("DEEPLINK", "THi sis the dynamic link " + shareURL);


                        createAlertDialog();

                    } else {
                        // Error
                        // ...
                    }
                });


    }

    private void createAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(requireActivity(), R.style.MyAlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(requireActivity());
        }
        builder.setTitle(getString(R.string.share_title))
                .setMessage(getString(R.string.share_subtitle))
                .setCancelable(false)
                .setPositiveButton("Share", (dialog, which) -> {

                    String shareText = getString(R.string.share_message) +
                            shareURL;

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_SUBJECT, R.string.share_subject);
                    share.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(share, "Share Via"));
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .setIcon(R.drawable.ic_check);
        if (!isDetached()) {
            builder.show();
        }
    }

    private void clearAllEditTexts() {
        question.getEditText().setText("");
        answer.setSelection(0);
        o1.getEditText().setText("");
        o2.getEditText().setText("");
        o3.getEditText().setText("");
        o4.getEditText().setText("");

        Handler mHandler = new Handler();
        mHandler.post(
                () -> {
                    InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.toggleSoftInputFromWindow(question.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                    }
                    question.requestFocus();
                });

    }

    private boolean addToList() {
        if (getTextFromEdittext() == 7) {
            InsertModel model = new InsertModel();

            model.setTitleStr(titleStr);
            model.setQuestionStr(questionStr);
            model.setAnsStr(ansStr);
            model.setO1Str(o1Str);
            model.setO2Str(o2Str);
            model.setO3Str(o3Str);
            model.setO4Str(o4Str);


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userID = user.getEmail();
                model.setUserID(userID);
            }
            insertModelList.add(model);

            previewViewModel.setUserLiveData(insertModelList);
            previewViewModel.setTitle(titleStr);


            return true;
        } else {
            return false;
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_insert_fragment, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publish:
                // do something
                publish();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    private void showToast(String edittextName) {
        Snackbar snackbar;
        snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content),
                edittextName + " cannot be empty.", Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.red));
        snackbar.show();

    }


    private int getTextFromEdittext() {
        int returnValue = 0;
        if (!isEmpty(question.getEditText())) {
            questionStr = question.getEditText().getText().toString();
            returnValue++;
        } else {
            showToast(getString(R.string.question));
        }
        if (answer.getSelectedItem().toString().trim().length() != 0) {
            ansStr = answer.getSelectedItem().toString();
            Log.d("INSERTFRAG", "The Answer is " + ansStr);
            returnValue++;
        } else {
            showToast(getString(R.string.answer));
        }


        if (!isEmpty(o1.getEditText())) {
            o1Str = o1.getEditText().getText().toString();
            returnValue++;
        } else {
            showToast(getString(R.string.opt_a));
        }

        if (!isEmpty(o2.getEditText())) {
            o2Str = o2.getEditText().getText().toString();
            returnValue++;
        } else {
            showToast(getString(R.string.opt_b));
        }

        if (!isEmpty(o3.getEditText())) {
            o3Str = o3.getEditText().getText().toString();
            returnValue++;
        } else {
            showToast(getString(R.string.opt_c));
        }

        if (!isEmpty(o4.getEditText())) {
            o4Str = o4.getEditText().getText().toString();
            returnValue++;
        } else {
            showToast(getString(R.string.opt_d));
        }
        if (!isEmpty(quiz_title.getEditText())) {
            titleStr = quiz_title.getEditText().getText().toString();
            Log.d("INSERTFRAG", "This is title " + titleStr);
            returnValue++;
        } else {
            showToast(getString(R.string.title));
        }


        return returnValue;
    }


    class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private PreviewRVAdapter mAdapter;

        SwipeToDeleteCallback(PreviewRVAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(position);
        }
    }
}
