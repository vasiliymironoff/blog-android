package com.example.socialandroid.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.NewPost;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.service.PreferenceService;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PostDialog extends DialogFragment {


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog dialog =  new AlertDialog.Builder(getContext(),R.style.AlertDialog).setView(R.layout.post_dialog)
                .setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog view = (Dialog) dialog;
                        EditText title = view.findViewById(R.id.title);
                        EditText text = view.findViewById(R.id.text);
                        Log.d("tag", text.getText().toString());
                        NewPost post = new NewPost(
                                PreferenceService.getId(getContext()),
                                title.getText().toString(),
                                text.getText().toString()
                        );
                        Single.just(App.getService().getApi().postPost(PreferenceService.getToken(getContext()),post)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BiConsumer<Post, Throwable>() {
                                    @Override
                                    public void accept(Post post, Throwable throwable) throws Throwable {
                                        if (throwable != null) {
                                            throwable.printStackTrace();
                                        }
                                    }
                                }));

                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle("Новый пост")
                .create();

        return dialog;
    }
}
