package com.example.socialandroid.ui.login;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialandroid.R;
import com.example.socialandroid.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private LoginFragmentBinding binding;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(LoginViewModel.class);
        binding.setModel(mViewModel);
        binding.getRoot().findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //проверяем и отправляем
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_customFragment);
            }
        });
        binding.getRoot().findViewById(R.id.is_register).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mViewModel.isRegister.set(!mViewModel.isRegister.get());
            }
        });

    }

}