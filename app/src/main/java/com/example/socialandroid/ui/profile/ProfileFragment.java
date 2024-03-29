package com.example.socialandroid.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Post;
import com.example.socialandroid.api.model.PostForProfile;
import com.example.socialandroid.api.model.Profile;
import com.example.socialandroid.databinding.ProfileFragmentBinding;
import com.example.socialandroid.service.BitmapService;
import com.example.socialandroid.service.PreferenceService;
import com.example.socialandroid.ui.PostsAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    RecyclerView recyclerView;
    PostsAdapter adapter;
    ProfileFragmentBinding binding;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.bind(view);
        recyclerView = view.findViewById(R.id.recycler_profile);
        adapter = new PostsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel.isMy.set(false);

        mViewModel.loadProfile(getArguments().getInt("ID"));
        binding.setModel(mViewModel);
        mViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<List<PostForProfile>>() {
            @Override
            public void onChanged(List<PostForProfile> postForProfiles) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mViewModel.username.get());
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
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(binding.getRoot()).popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}