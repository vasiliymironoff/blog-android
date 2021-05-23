package com.example.socialandroid.ui.profile;

import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialandroid.App;
import com.example.socialandroid.api.model.PostForProfile;
import com.example.socialandroid.api.model.ProfileDetail;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFilter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {
    public ObservableField<String> status = new ObservableField<String>();
    public ObservableField<String> about = new ObservableField<String>();
    public ObservableField<String> sex = new ObservableField<String>();
    public ObservableField<String> birthDay = new ObservableField<String>();
    public ObservableField<String> countPost = new ObservableField<String>();
    public ObservableField<String> email = new ObservableField<String>();
    public ObservableField<String> username = new ObservableField<>();
    public String imageUrl;
    public MutableLiveData<List<PostForProfile>> posts = new MutableLiveData<>();

    void loadProfile(int id) {
        Single.just(App.getService().getApi().getProfileDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<ProfileDetail, Throwable>() {
                    @Override
                    public void accept(ProfileDetail profileDetail, Throwable throwable) throws Throwable {
                        if (throwable != null){
                            throwable.printStackTrace();
                        } else {
                            status.set("Статус: " + profileDetail.getStatus());
                            about.set("Подробная информация:" + profileDetail.getAbout());
                            if (profileDetail.getIsMen()) {
                                sex.set("Пол: мужской");
                            } else {
                                sex.set("Пол: женский");
                            }
                            birthDay.set("День рождения: " + profileDetail.getBirthDate());
                            countPost.set("Количество постов: " + Integer.toString(profileDetail.getPosts().size()));
                            email.set("Email: " + profileDetail.getEmail());
                            username.set(profileDetail.getUsername());
                            imageUrl = profileDetail.getImage();
                            posts.setValue(profileDetail.getPosts());
                        }
                    }
                }));
    }

    LiveData<List<PostForProfile>> getPosts(){
        return posts;
    }
}