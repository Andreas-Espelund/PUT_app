package com.example.put_app.ui.main.upload;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.put_app.databinding.FragmentUploadBinding;
import com.example.put_app.util.PUTApplication;
import com.example.put_app.util.Person;
import com.example.put_app.util.RepositoryViewModelFactory;
import com.google.android.material.slider.Slider;

public class UploadFragment extends Fragment {

    private UploadViewModel viewModel;
    private FragmentUploadBinding binding;
    private ActivityResultLauncher<Intent> takePictureResultLauncher;
    private Bitmap imageToUpload;

    private EditText people;
    private EditText description;
    private Button btn;
    private ImageView imageView;
    private boolean imageTaken = false;

    private String nextIndexUpdateTag = "";
    public static UploadFragment newInstance() {
        return new UploadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUploadBinding.inflate(inflater);

        // init viewmodel
        RepositoryViewModelFactory factory = new RepositoryViewModelFactory(requireActivity());
        viewModel = new ViewModelProvider(this, factory).get(UploadViewModel.class);

        btn = binding.takePhoto;
        people = binding.photoPeople;
        description = binding.photoDescription;
        imageView = binding.imageView;
        btn.setOnClickListener(v -> uploadPhoto());



        takePictureResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageToUpload = imageBitmap;
                        imageView.setImageBitmap(imageBitmap); // Set the image in the ImageView
                        showViews();
                    }
                }
        );

        viewModel.getUploadResult().observe(getViewLifecycleOwner(), result -> {
            Toast.makeText(this.getActivity(),result ? "Suksess" : "Feilet", Toast.LENGTH_SHORT).show();
        });

        viewModel.getMyTags().observe(getViewLifecycleOwner(), result -> {
            Log.d("TAGS", result.getNumberOfTags());
            nextIndexUpdateTag = result.getNumberOfTags();
        });



        openCamera();

        viewModel.fetchTags();

        return binding.getRoot();
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, open the camera
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureResultLauncher.launch(takePictureIntent);
        } else {
            // Request camera permission
            requestPermissions(new String[] {android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    private void showViews() {
        description.setVisibility(View.VISIBLE);
        people.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
    }


    private void uploadPhoto() {
        viewModel.uploadPhoto(
                "0",
                imageToUpload,
                nextIndexUpdateTag,
                description.getText().toString(),
                "TODO THIS ONE ALSO",
                "TODO LOCATION",
                people.getText().toString()
        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(); // Retry
            } else {
                Toast.makeText(getContext(), "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static final int REQUEST_CAMERA_PERMISSION = 1;

}