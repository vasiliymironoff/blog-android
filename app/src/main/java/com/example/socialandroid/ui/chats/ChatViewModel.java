package com.example.socialandroid.ui.chats;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialandroid.App;
import com.example.socialandroid.api.model.Profile;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatViewModel extends ViewModel {

    CompositeDisposable disposable = new CompositeDisposable();

    MutableLiveData<List<Profile>> profiles = new MutableLiveData<>();


    LiveData<List<Profile>> getProfiles() {
        return profiles;
    }


    void loadProfiles() {
        disposable.add(App.getService().getApi().getListProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<Profile>, Throwable>() {
                    @Override
                    public void accept(List<Profile> profiles, Throwable throwable) throws Throwable {
                        Log.d("tag", "accetp");
                        if (throwable != null) {
                            Log.d("tag", "error");
                            throwable.printStackTrace();
                        } else {
                            for (Profile profile : profiles) {
                                Log.d("tag", profile.getUsername());
                            }
                            setProfiles(profiles);
                            App.profiles = profiles;
                        }
                    }
                }));
    }

    void setProfiles(List<Profile> profiles) {
        this.profiles.setValue(profiles);
    }
}