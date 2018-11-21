package com.udacity.quiztime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.quiztime.R;
import com.udacity.quiztime.models.QuizList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class QuizListAdapter extends PagedListAdapter<QuizList, RecyclerView.ViewHolder> {

    private final CustomItemClickListener clickListener;


    public QuizListAdapter(Context context, CustomItemClickListener listener) {
        super(QuizList.DIFF_CALL);
        clickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_list_single_item, parent, false);

        return new MyViewHolder1(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1) {
            MyViewHolder1 movieViewHolder = (MyViewHolder1) holder;
            QuizList movie = getItem(position);
            movieViewHolder.bind(movie);

        }
    }


    public interface CustomItemClickListener extends View.OnClickListener {
        void onItemClick(QuizList movie);
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {
        final ImageView user_avatar;
        final TextView quiz_title;
        final TextView author;
        final TextView noofq;
        final CardView cardView;

        MyViewHolder1(View itemView) {
            super(itemView);
            quiz_title = itemView.findViewById(R.id.quizTitle);
            user_avatar = itemView.findViewById(R.id.user_avatar);
            author = itemView.findViewById(R.id.author);
            noofq = itemView.findViewById(R.id.noofq);
            cardView = itemView.findViewById(R.id.cardView);

        }

        void bind(final QuizList movie) {


            quiz_title.setText(movie.getTitle());
            Picasso.get()
                    .load(movie.getThumb())
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.loader)
                    .into(user_avatar);
            author.setText(movie.getAuthor());
            noofq.setText(String.valueOf(movie.getNoofq()));

            cardView.setOnClickListener(view -> clickListener.onItemClick(movie));
        }
    }


}
