package com.example.put_app.ui.main.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.put_app.databinding.FragmentMapBinding;
import com.example.put_app.util.RepositoryViewModelFactory;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // init binding
        binding = FragmentMapBinding.inflate(inflater, container, false);

        // init viewmodel
        RepositoryViewModelFactory factory = new RepositoryViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(this,factory).get(MapViewModel.class);


        final TextView textView = binding.textHome;
        viewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}