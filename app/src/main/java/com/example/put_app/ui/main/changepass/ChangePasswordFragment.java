package com.example.put_app.ui.main.changepass;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.put_app.util.PUTApplication;
import com.example.put_app.util.Person;
import com.example.put_app.databinding.FragmentChangePasswordBinding;
import com.example.put_app.util.RepositoryViewModelFactory;

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel viewModel;
    private FragmentChangePasswordBinding binding;
    private PUTApplication app;



    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // set up binding of views
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);

        // init viewmodel
        RepositoryViewModelFactory factory = new RepositoryViewModelFactory(requireActivity());
        viewModel = new ViewModelProvider(this,factory).get(ChangePasswordViewModel.class);

        EditText pass = binding.newPassword;
        EditText passVerify = binding.newPasswordVerify;
        Button cancelBtn = binding.cancelButton;
        Button confirmBtn = binding.changepassButton;

        // navigate back button
        cancelBtn.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        confirmBtn.setOnClickListener(v -> handleChangePass(
                pass.getText().toString(),
                passVerify.getText().toString()
            )
        );

        viewModel.getPasswordChangeResult().observe(getViewLifecycleOwner(), isSuccess -> {
            String message = isSuccess ? "Password changed" : "Failed to change password";
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            passVerify.setText("");
            pass.setText("");
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleChangePass(String pass, String passVerify) {

        if (pass.equals(passVerify)) {
            Person user = app.getCurrentUser();
            viewModel.changePassword(user.getEmail(), pass);
        } else {
            Toast.makeText(this.getActivity(), "Passwords does not match", Toast.LENGTH_SHORT).show();
        }
    }

}