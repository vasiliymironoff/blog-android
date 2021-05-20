package com.example.socialandroid.ui.chats;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Profile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestCreator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.Holder> {

    List<Profile> profiles = new ArrayList<>();
    Chatable fragment;

    ProfileAdapter(Chatable fragment){
        this.fragment = fragment;
    }
    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new Holder(view, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        holder.bind(profiles.get(position));
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView textView;
        View itemView;
        Chatable fragment;


        public Holder(@NonNull @NotNull View itemView, Chatable fragment) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_profile);
            textView = itemView.findViewById(R.id.username);
            this.itemView = itemView;
            this.fragment = fragment;
        }

        public void bind(Profile profile) {
            textView.setText(profile.getUsername());
            Single.fromCallable(new Callable<RequestCreator>() {
                @Override
                public RequestCreator call() throws Exception {
                    return Picasso.get().load(profile.getImage());
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BiConsumer<RequestCreator, Throwable>() {
                        @Override
                        public void accept(RequestCreator requestCreator, Throwable throwable) throws Throwable {
                            if (throwable != null) {
                                throwable.printStackTrace();
                            } else {
                                requestCreator.centerCrop().fit().into(circleImageView);
                            }
                        }
                    });
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.toProfile(profile.getId());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.toMessage(profile.getId());
                }
            });
        }
    }
}
