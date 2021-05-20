package com.example.socialandroid.ui.chats;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Profile;

import java.util.List;

public class ChatFragment extends Fragment implements Chatable{

    private ChatViewModel mViewModel;
    private RecyclerView recyclerView;
    private ProfileAdapter adapter;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        recyclerView = view.findViewById(R.id.chat_recycler);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        adapter = new ProfileAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mViewModel.getProfiles().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                adapter.setProfiles(profiles);
                adapter.notifyDataSetChanged();
            }
        });
        if (App.profiles == null) {
            mViewModel.loadProfiles();
        } else {
            mViewModel.profiles.setValue(App.profiles);
        }

    }

    @Override
    public void toMessage(int id) {
        Navigation.findNavController(recyclerView).navigate(R.id.action_chat_fragment_to_messageActivity);
    }

    @Override
    public void toProfile(int id) {
        Log.d("tag", "profile");
    }


}