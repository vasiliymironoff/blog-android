package com.example.socialandroid.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.databinding.ItemPostBinding;
import com.example.socialandroid.service.BitmapService;
import com.example.socialandroid.service.PreferenceService;
import com.example.socialandroid.ui.profile.MyProfileFragment;
import com.example.socialandroid.ui.profile.ProfileFragment;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder> {

    List<Post> posts = new ArrayList<>();
    Fragment fragment;

    public PostsAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new Holder(view, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        if (posts.get(position) != null) {
            holder.bind(posts.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    static class Holder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        Fragment fragment;
        ItemPostBinding binding;

        public Holder(@NonNull @NotNull View itemView, Fragment fragment) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            circleImageView = itemView.findViewById(R.id.person_image);
            this.fragment = fragment;
        }

        void bind(Post post) {
            binding.setPost(post);
            if (post.getAuthor().getImage() != null ) {
                BitmapService.getInstance().loadBitmap(post.getAuthor().getImage(), circleImageView);
            }
            binding.getRoot().findViewById(R.id.profile_min).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", post.getAuthor().getId());
                    //Переход в наш аккаунт
                    if (post.getAuthor().getId() == PreferenceService.getId(binding.getRoot().getContext())){
                        if (!(fragment.getClass() == MyProfileFragment.class)){
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_home_fragment_to_my_profile_fragment);
                        }
                    } else {
                        //Переход в чужой аккаунт
                        if (fragment.getClass() == ProfileFragment.class) {
                            //С ProfileFragment to ProfileFragment
                           Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_self, bundle);
                        } else {
                            //C HomeFragment to ProfileFragment
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_home_fragment_to_profileFragment, bundle);
                        }

                    }
                }
            });
        }

    }
}