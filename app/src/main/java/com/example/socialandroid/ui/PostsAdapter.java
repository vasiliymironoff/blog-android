package com.example.socialandroid.ui;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.databinding.ItemPostBinding;
import com.example.socialandroid.service.BitmapService;

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
            if (post.getAuthor().getImage() != null) {
                BitmapService.getInstance().loadBitmap(post.getAuthor().getImage(), circleImageView);

            }
        }

    }
}