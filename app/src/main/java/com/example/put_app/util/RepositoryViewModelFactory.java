package com.example.put_app.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.put_app.model.DataRepository;

public class RepositoryViewModelFactory implements ViewModelProvider.Factory {
    private final DataRepository repository;

    public RepositoryViewModelFactory(Context context) {
        if (context.getApplicationContext() instanceof PUTApplication) {
            PUTApplication app = (PUTApplication) context.getApplicationContext();
            this.repository = app.getRepository();
        } else {
            throw new IllegalStateException("Context does not have the correct application type");
        }
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        try {
            return modelClass.getConstructor(DataRepository.class).newInstance(repository);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}

