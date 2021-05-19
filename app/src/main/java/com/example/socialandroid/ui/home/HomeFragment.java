package com.example.socialandroid.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Post;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView recyclerView;
    private PostsAdapter adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        adapter = new PostsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        mViewModel.updatePosts();
        mViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                Log.d("tag", "new" + posts.size());
                adapter.setPosts(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }


}