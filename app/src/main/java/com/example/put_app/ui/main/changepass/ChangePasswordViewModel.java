package com.example.put_app.ui.main.changepass;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.put_app.model.DataRepository;

public class ChangePasswordViewModel extends ViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<Boolean> passwordChangeResult = new MutableLiveData<>();
    public ChangePasswordViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public MutableLiveData<Boolean> getPasswordChangeResult() {
        return passwordChangeResult;
    }

    public void changePassword(String email, String newPassword) {
        dataRepository.changePassword(email, newPassword,
                response -> {
                    passwordChangeResult.setValue(true);
                },
                error -> {
                    Log.d("PASSWORD", error.toString());
                    passwordChangeResult.setValue(false);
                }
        );
    }
}