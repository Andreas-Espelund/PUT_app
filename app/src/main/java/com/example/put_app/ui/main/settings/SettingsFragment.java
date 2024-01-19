package com.example.put_app.ui.main.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.put_app.ui.login.LoginActivity;
import com.example.put_app.util.PUTApplication;
import com.example.put_app.util.Person;
import com.example.put_app.R;
import com.example.put_app.databinding.FragmentSettingsBinding;
import com.example.put_app.util.RepositoryViewModelFactory;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private PUTApplication app;
    private Person user;
    private SettingsViewModel viewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        // init viewmodel
        RepositoryViewModelFactory factory = new RepositoryViewModelFactory(requireActivity());
        viewModel = new ViewModelProvider(this, factory).get(SettingsViewModel.class);


        Button changepassButton = binding.changepassButton;
        Button logOutButton = binding.logoutButton;
        changepassButton.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.change_password_action));
        logOutButton.setOnClickListener(v -> signOut());

        TextView name = binding.nameTextView;
        TextView birthdate = binding.birthDateTextView;
        TextView city = binding.cityTextView;

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                name.setText(String.format("Name: %s %s", user.getFirstName(), user.getLastName()));
                birthdate.setText(String.format("Birthdate: %s", user.getYearOfBirth()));
                city.setText(String.format("City: %s", user.getLivingCity()));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void signOut() {
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}