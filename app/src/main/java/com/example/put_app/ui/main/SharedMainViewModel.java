package com.example.put_app.ui.main;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.put_app.model.DataRepository;

public class SharedMainViewModel extends ViewModel {
    private final MutableLiveData<Location> location = new MutableLiveData<Location>();
    private DataRepository repository;

    public SharedMainViewModel() {

    }

    public LiveData<Location> getLastKnownLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location.setValue(loc);
    }
}
