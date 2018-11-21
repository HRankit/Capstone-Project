package com.udacity.quiztime.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.udacity.quiztime.R;
import com.udacity.quiztime.data.database.QuizListEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;
import static com.udacity.quiztime.widget.NewAppWidget.POSITION_TO_SHOW;
import static com.udacity.quiztime.widget.NewAppWidget.TOAST_ACTION;

public class SavedQuizWidgetService extends RemoteViewsService {
    private int position = 0;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent != null) {
            position = intent.getIntExtra(POSITION_TO_SHOW, 0);
        }
        return new SavedQuizWidgetViewFactory(this.getApplicationContext(), position);
    }

}

class SavedQuizWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<WidgetItem> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private final List<String> mImages = new ArrayList<>();
    private int mStepsCount;


    SavedQuizWidgetViewFactory(Context context, int pos) {
        mContext = context;
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.


        SharedPreferences sharedpreferences = mContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<List<QuizListEntry>>() {
        }.getType();
        String json = sharedpreferences.getString("widgetdetails", null);
        if (!isEmpty(json)) {
            List<QuizListEntry> arrayList = gson.fromJson(json, type);
            mStepsCount = arrayList.size();
            for (int i = 0; i < mStepsCount; i++) {
                mWidgetItems.add(new WidgetItem(arrayList.get(i).getTitle() + " by " + arrayList.get(i).getAuthor()));
                mImages.add(arrayList.get(i).getThumb());
            }
        }
    }


    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_single_row);
        rv.setTextViewText(R.id.ingredientName, mWidgetItems.get(position).text);

        Bundle extras = new Bundle();
        extras.putInt(NewAppWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.setAction(TOAST_ACTION);
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.row_layout, fillInIntent);

        try {
            Bitmap b = Picasso.get().load(mImages.get(position)).get();
            rv.setImageViewBitmap(R.id.image, b);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return rv;
    }


    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        mWidgetItems.clear();
    }

    public int getCount() {
        return mStepsCount;
    }


    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }
}