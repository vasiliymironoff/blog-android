package com.example.socialandroid.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Id;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.api.model.PostForProfile;
import com.example.socialandroid.api.model.Profile;
import com.example.socialandroid.databinding.ProfileFragmentBinding;
import com.example.socialandroid.service.BitmapService;
import com.example.socialandroid.service.PreferenceService;
import com.example.socialandroid.ui.PostDialog;
import com.example.socialandroid.ui.PostsAdapter;
import com.example.socialandroid.ui.activity.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    ProfileFragmentBinding binding;
    RecyclerView recyclerView;
    PostsAdapter adapter;
    FloatingActionButton fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Моя страница");

        setHasOptionsMenu(true);
        binding = DataBindingUtil.bind(view);
        recyclerView = view.findViewById(R.id.recycler_profile);
        adapter = new PostsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostDialog().show(getActivity().getSupportFragmentManager(), (String) v.getTag());
                mViewModel.loadProfile(PreferenceService.getId(getContext()));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel.loadProfile(PreferenceService.getId(getContext()));
        binding.setModel(mViewModel);
        mViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<List<PostForProfile>>() {
            @Override
            public void onChanged(List<PostForProfile> postForProfiles) {
                if (mViewModel.imageUrl != null){
                    BitmapService.getInstance().loadBitmap(mViewModel.imageUrl, binding.getRoot().findViewById(R.id.image_my_profile));
                }
                int id = PreferenceService.getId(getContext());
                Profile profile = new Profile(id, mViewModel.username.get(), mViewModel.imageUrl);
                List<Post> posts = new ArrayList<>();
                for (PostForProfile post : postForProfiles) {
                    Post newPost = new Post(id,profile , post.getTitle(), post.getText(), post.getTime());
                    posts.add(newPost);
                }
                adapter.setPosts(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                String token = PreferenceService.getToken(getContext());
                App.getService().getApi().logout("Token " + token);
                PreferenceService.putToken(getContext(), "");
                PreferenceService.putId(getContext(), -1);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}