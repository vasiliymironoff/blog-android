package com.example.socialandroid.ui.login;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> password_repeat = new ObservableField<>();
    public ObservableBoolean isRegister = new ObservableBoolean();
}