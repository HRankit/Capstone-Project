package com.udacity.quiztime.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.udacity.quiztime.R;
import com.udacity.quiztime.models.InsertModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PreviewRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<InsertModel> mList;
    private final Activity mActivity;
    private InsertModel mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;

    public PreviewRVAdapter(Activity activity, List<InsertModel> list) {
        super();
        mActivity = activity;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preview_single_item, parent, false);

        return new PreviewRVAdapter.MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PreviewRVAdapter.MyViewHolder1) {
            PreviewRVAdapter.MyViewHolder1 movieViewHolder = (PreviewRVAdapter.MyViewHolder1) holder;
            InsertModel quiz = mList.get(position);
            movieViewHolder.bind(quiz);
        }
    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = mList.get(position);
        mRecentlyDeletedItemPosition = position;

        mList.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = mActivity.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, mActivity.getString(R.string.quiz_removed),
                Snackbar.LENGTH_LONG);
        snackbar.setAction(mActivity.getString(R.string.undo), v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        mList.add(mRecentlyDeletedItemPosition, mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {

        final TextView question, o1, o2, o3, o4, ans;


        MyViewHolder1(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            o1 = itemView.findViewById(R.id.o1);
            o2 = itemView.findViewById(R.id.o2);
            o3 = itemView.findViewById(R.id.o3);
            o4 = itemView.findViewById(R.id.o4);
            ans = itemView.findViewById(R.id.ans);
        }

        void bind(final InsertModel quiz) {
            String Q = quiz.getQuestionStr();
            String O1 = quiz.getO1Str();
            String O2 = quiz.getO2Str();
            String O3 = quiz.getO3Str();
            String O4 = quiz.getO4Str();
            String answer = quiz.getAnsStr();


            question.setText(Q);
            o1.setText(O1);
            o2.setText(O2);
            o3.setText(O3);
            o4.setText(O4);
            ans.setText(answer);
        }
    }
}
