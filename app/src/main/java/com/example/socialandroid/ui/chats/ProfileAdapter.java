package com.example.socialandroid.ui.chats;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Profile;
import com.example.socialandroid.service.BitmapService;
import com.example.socialandroid.service.PreferenceService;
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

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new Holder(view);
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


        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_profile);
            textView = itemView.findViewById(R.id.username);
            this.itemView = itemView;

        }

        public void bind(Profile profile) {
            textView.setText(profile.getUsername());
            if (profile.getImage() != null) {
                BitmapService.getInstance().loadBitmap(profile.getImage(), circleImageView);
            }
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (profile.getId() == PreferenceService.getId(itemView.getContext())){
                        Navigation.findNavController(itemView).navigate(R.id.action_chat_fragment_to_my_profile_fragment);
                    }{
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID", profile.getId());
                        Navigation.findNavController(itemView).navigate(R.id.action_chat_fragment_to_profile_fragment, bundle);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("tag", profile.getUsername() + profile.getId());
                    Bundle bundle = new Bundle();
                    bundle.putString("USERNAME", profile.getUsername());
                    bundle.putString("IMAGE", profile.getImage());
                    bundle.putInt("ID", profile.getId());
                    Navigation.findNavController(itemView).navigate(R.id.action_chat_fragment_to_messageActivity, bundle);
                }
            });
        }
    }
}
