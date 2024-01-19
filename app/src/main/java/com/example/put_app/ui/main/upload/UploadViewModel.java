package com.example.put_app.ui.main.upload;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.put_app.model.DataRepository;
import com.example.put_app.util.ImageConverter;
import com.example.put_app.util.Tags;

import org.json.JSONException;

public class UploadViewModel extends ViewModel {
    private DataRepository repository;

    private MutableLiveData<Boolean> uploadResult = new MutableLiveData<>();
    private MutableLiveData<Tags> tags = new MutableLiveData<>();
    public UploadViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> getUploadResult() { return uploadResult; }


    public LiveData<Tags> getMyTags() {
        return tags;
    }


    public void fetchTags() {
        //String userID = repository.getCurrentUser().getId();
        String userID = "1";
        repository.getMyTags(userID,
                response -> {
                    try {
                        tags.setValue(Tags.parseFromJson(response));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    Log.d("TAGS", "error while feching tags " + error.toString());
                }
        );

    }
    public void uploadPhoto(
            String tagID,
            Bitmap bitmap,
            String indexUpdateTag,
            String newTagDes,
            String newTagPho,
            String newTagLoc,
            String newTagPeopleName
    ) {
        String encodedImage = ImageConverter.bitmapToBase64(bitmap);

        //String userID = repository.getCurrentUser().getId();
        String userID = "1";
        String filename = String.format("user_%s__file_%s",userID,indexUpdateTag);
        repository.uploadPhoto(userID,tagID,filename,encodedImage,
                response -> {
                    repository.insertNewTag(userID, indexUpdateTag, newTagDes, newTagPho, newTagLoc, newTagPeopleName,
                    response1 -> {
                        uploadResult.setValue(true);
                    }, error -> {
                        uploadResult.setValue(false);
                    });
                },
                error -> {
                    uploadResult.setValue(false);
                }
        );
    }

}