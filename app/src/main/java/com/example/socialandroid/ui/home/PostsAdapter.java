package com.example.socialandroid.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.databinding.ItemPostBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder> {

    List<Post> posts = new ArrayList<>();


    void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new Holder(view);
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
        ItemPostBinding binding;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(Post post) {
            binding.setPost(post);
        }
    }

}
