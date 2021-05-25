package com.example.socialandroid.ui.message;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialandroid.App;
import com.example.socialandroid.api.model.Message;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MessageViewModel extends ViewModel {
    public ObservableField<String> text = new ObservableField<>();
    MutableLiveData<List<Message>> message = new MutableLiveData<List<Message>>();
    Bitmap bitmap;

    void loadMessage(int user1, int user2) {
        Single.just(App.getService().getApi().getMessage(user1, user2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<Message>, Throwable>() {
                    @Override
                    public void accept(List<Message> listSingle, Throwable throwable) throws Throwable {
                        if (throwable != null) {
                            throwable.printStackTrace();
                        } else {
                            message.setValue(listSingle);
                        }
                    }
                }));
    }

    LiveData<List<Message>> getMessage() {
        return message;
    }

}