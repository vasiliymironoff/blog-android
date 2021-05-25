package com.example.socialandroid.ui.login;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Id;
import com.example.socialandroid.api.model.NewUser;
import com.example.socialandroid.api.model.Profile;
import com.example.socialandroid.api.model.Token;
import com.example.socialandroid.api.model.User;
import com.example.socialandroid.databinding.LoginFragmentBinding;
import com.example.socialandroid.service.PreferenceService;
import com.example.socialandroid.ui.activity.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginFragment extends Fragment {



    private LoginViewModel mViewModel;
    private LoginFragmentBinding binding;


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
                if (mViewModel.username.get().trim().equals("")) {
                    showToast("Не введено имя пользователя");
                    return;
                }
                if (mViewModel.password.get().trim().equals("")) {
                    showToast("Не введен пароль");
                    return;
                }
                NewUser newUser = new NewUser(mViewModel.username.get(), mViewModel.email.get(), mViewModel.password.get());
                if (mViewModel.isRegister.get()) {
                    if (!mViewModel.password.get().trim().equals(mViewModel.password_repeat.get().trim())) {

                        showToast("Пароли не совпадают");
                        mViewModel.password.set("");
                        mViewModel.password_repeat.set("");
                        return;
                    }
                    createUser(newUser);
                } else {
                    getToken(newUser);
                }

            }
        });
        binding.getRoot().findViewById(R.id.is_register).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewModel.isRegister.set(!mViewModel.isRegister.get());
            }
        });

    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    void createUser(NewUser newUser) {
        Single.just(App.getService().getApi().createUser(newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<User, Throwable>() {
                    @Override
                    public void accept(User user, Throwable throwable) throws Throwable {
                        if (throwable != null) {
                            showToast("Произошла ошибка");
                        } else {
                            PreferenceService.putId(getContext(), user.getId());
                            PreferenceService.putUserName(getContext(), user.getUsername());
                            getToken(newUser);
                        }
                    }
                }));
    }

    void getToken(NewUser newUser) {

        Single.just(App.getService().getApi().getToken(newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Token, Throwable>() {
                    @Override
                    public void accept(Token token, Throwable throwable) throws Throwable {
                        if (throwable != null) {
                            showToast("Ошибка получения токена");
                        } else {
                            PreferenceService.putToken(getContext(), token.getToken());
                            if (PreferenceService.getId(getContext()) == -1) {
                                getIdForLogin();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            } else {
                                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_customFragment);
                            }
                        }
                    }
                }));

    }

    void getIdForLogin() {
        App.getService().getApi().getMe(PreferenceService.getToken(getContext()))
                .subscribe(new BiConsumer<User, Throwable>() {
            @Override
            public void accept(User user, Throwable throwable) throws Throwable {
                if (throwable != null) {
                    throwable.printStackTrace();
                } else {
                    PreferenceService.putId(getContext(), user.getId());
                    PreferenceService.putUserName(getContext(), user.getUsername());
                }
            }
        });
    }

}