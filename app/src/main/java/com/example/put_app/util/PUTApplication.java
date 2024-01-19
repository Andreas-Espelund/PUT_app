package com.example.put_app.util;

import android.app.Application;

import com.example.put_app.model.DataRepository;
import com.example.put_app.util.Person;

public class PUTApplication extends Application {

    private Person currentUser;
    private DataRepository repository;


    @Override
    public void onCreate() {
        super.onCreate();
        repository = new DataRepository(this);
    }

    public void setCurrentUser(Person user) {
        this.currentUser = user;
    }

    public Person getCurrentUser() {
        return currentUser;
    }


    public DataRepository getRepository() {
        return repository;
    }
}
