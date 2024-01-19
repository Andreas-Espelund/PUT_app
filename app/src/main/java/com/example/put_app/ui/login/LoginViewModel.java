package com.example.put_app.ui.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.put_app.util.Person;
import com.example.put_app.model.DataRepository;

import org.json.JSONException;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Person> authState = new MutableLiveData<>();

    private DataRepository repository;

    public LoginViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<Person> getData() {
        return authState;
    }

    public void logIn(String email, String password) {
        repository.logIn(email, password,
                response -> {
                    Log.d("AUTH", response.toString());
                    try {
                        Person p = Person.parsePerson(response);
                        repository.setCurrentUser(p);
                        authState.setValue(p);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    Log.d("AUTH ERROR BRUH", error.toString());
                    authState.setValue(null);
                }
        );
    }

}
