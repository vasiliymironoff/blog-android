package com.example.socialandroid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialandroid.App;
import com.example.socialandroid.api.model.Post;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    CompositeDisposable disposable = new CompositeDisposable();

    void updatePosts() {

        disposable.add(App.getService().getApi().getListPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<Post>, Throwable>() {
                    @Override
                    public void accept(List<Post> posts, Throwable throwable) throws Throwable {
                        if (throwable != null) {
                            throwable.printStackTrace();
                        } else {
                            setPosts(posts);
                            App.posts = posts;
                        }
                    }
                }));
    }

    void setPosts(List<Post> posts) {
        this.posts.setValue(posts);
    }

    LiveData<List<Post>> getPosts() {
        return posts;
    }
}