package com.example.put_app.ui.main.gallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.put_app.R;
import com.example.put_app.databinding.FragmentGalleryBinding;
import com.example.put_app.util.PUTApplication;
import com.example.put_app.util.RepositoryViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private FragmentGalleryBinding binding;
    private GalleryViewModel viewModel;
    private GalleryAdapter galleryAdapter;

    private List<Bitmap> images;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        // viewmodel init
        RepositoryViewModelFactory factory = new RepositoryViewModelFactory(requireActivity());
        viewModel = new ViewModelProvider(this,factory).get(GalleryViewModel.class);

        images = new ArrayList<>();
        // recyclerview init
        RecyclerView galleryRecyclerview = binding.galleryRecyclerview;
        galleryRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        galleryAdapter = new GalleryAdapter();
        galleryRecyclerview.setAdapter(galleryAdapter);
        galleryAdapter.setImages(images);

        // fab init
        FloatingActionButton fab = binding.galleryFab;
        fab.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.upload_action));

        // get the data from viewmodel
        viewModel.fetchImage("user_1__file_0");
        viewModel.fetchImage("user_1__file_1");
        viewModel.fetchImage("user_1__file_2");
        viewModel.fetchImage("user_1__file_3");
        viewModel.getImage().observe(getViewLifecycleOwner(), bitmap -> {
            List<Bitmap> newImg = images;
            newImg.add(bitmap);
            galleryAdapter.setImages(newImg);

        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}