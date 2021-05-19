package com.example.socialandroid.ui.custom;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class CustomViewModel extends ViewModel {
    public ObservableField<String> status = new ObservableField<>();
    public ObservableField<String> about = new ObservableField<>();
    public ObservableBoolean isMen = new ObservableBoolean();

}