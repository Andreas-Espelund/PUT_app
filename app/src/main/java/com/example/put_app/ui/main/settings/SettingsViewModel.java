package com.example.put_app.ui.main.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.put_app.util.Person;
import com.example.put_app.model.DataRepository;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<Person> user = new MutableLiveData<>();

    private DataRepository repository;
    public SettingsViewModel(DataRepository repository) {
        this.repository = repository;
        user.setValue(repository.getCurrentUser());

    }

    public LiveData<Person> getUser() {
        return user;
    }


}