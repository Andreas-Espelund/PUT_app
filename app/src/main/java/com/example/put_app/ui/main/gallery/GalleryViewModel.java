package com.example.put_app.ui.main.gallery;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.put_app.model.DataRepository;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<Bitmap> image = new MutableLiveData<>();

    private DataRepository repository;
    public GalleryViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<Bitmap> getImage() {
        return image;
    }


    public void fetchImage(String fileName) {

        repository.downloadPhoto(fileName,
                response -> {
                    image.setValue(response);
                },
                error -> {
                    Log.d("ERROR", "Download failed");
                }
        );
    }
}