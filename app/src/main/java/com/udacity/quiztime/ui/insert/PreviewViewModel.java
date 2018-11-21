package com.udacity.quiztime.ui.insert;

import android.app.Application;

import com.udacity.quiztime.models.InsertModel;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

class PreviewViewModel extends AndroidViewModel {

    private final MutableLiveData<List<InsertModel>> userLiveData;
    private final MutableLiveData<String> title;

    PreviewViewModel(Application application) {
        super(application);

        userLiveData = new MutableLiveData<>();
        title = new MutableLiveData<>();
    }

    LiveData<List<InsertModel>> getUserList() {
        return userLiveData;
    }

    void setUserLiveData(List<InsertModel> list) {
        userLiveData.postValue(list);
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String titles) {
        title.postValue(titles);
    }

}