package com.example.put_app.ui.main.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.put_app.model.DataRepository;

public class MapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapViewModel(DataRepository repository) {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}