package com.example.socialandroid.ui.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Message;
import com.example.socialandroid.databinding.MessageFragmentBinding;
import com.example.socialandroid.service.BitmapService;
import com.example.socialandroid.service.PreferenceService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MessageFragment extends Fragment {

    public static final int PICK_REQUEST = 234;

    MessageViewModel mViewModel;
    MessageFragmentBinding binding;
    RecyclerView recyclerView;
    MessageAdapter adapter;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        binding = DataBindingUtil.bind(view);
        recyclerView = view.findViewById(R.id.message_recycler);
        adapter = new MessageAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        binding.setModel(mViewModel);
        mViewModel.loadMessage(PreferenceService.getId(getContext()), getActivity().getIntent().getIntExtra("ID", -1));
        mViewModel.getMessage().observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.setMessageList(messages);
                adapter.notifyDataSetChanged();
            }
        });
        binding.getRoot().findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image = null;
                if (mViewModel.bitmap != null) {
                    image = BitmapService.getInstance().BitmapToBase64String(mViewModel.bitmap);
                }

                Message messageNew = new Message(mViewModel.text.get(), image, PreferenceService.getId(getContext()), getActivity().getIntent().getIntExtra("ID", -1));
                mViewModel.text.set("");
                Single.just(App.getService().getApi().postMessage(PreferenceService.getToken(getContext()),messageNew).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BiConsumer<Message, Throwable>() {
                            @Override
                            public void accept(Message message, Throwable throwable) throws Throwable {
                                if (throwable != null) {
                                    throwable.printStackTrace();
                                    Toast.makeText(getContext(), "Сообщение не доставлено", Toast.LENGTH_LONG).show();
                                    mViewModel.text.set(messageNew.getText());
                                } else {
                                    adapter.addMessage(message);
                                    adapter.notifyItemChanged(adapter.getItemCount());
                                    mViewModel.bitmap = null;
                                }
                            }
                        }));
            }
        });
        binding.getRoot().findViewById(R.id.attach_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == PICK_REQUEST && resultCode == Activity.RESULT_OK) {
            final InputStream is;
            try {
                Uri uri =  data.getData();
                is = getActivity().getContentResolver().openInputStream(uri);
                mViewModel.bitmap = BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}