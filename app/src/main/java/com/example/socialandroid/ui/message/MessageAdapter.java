package com.example.socialandroid.ui.message;


import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.Message;
import com.example.socialandroid.databinding.ItemMessageBinding;
import com.example.socialandroid.service.BitmapService;
import com.example.socialandroid.service.PreferenceService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    List<Message> messageList = new ArrayList<>();
    MessageFragment fragment;

    MessageAdapter(MessageFragment fragment) {
        this.fragment =fragment;
    }
    void setMessageList(List<Message> lists) {
        messageList = lists;
    }

    void addMessage(Message message){
        messageList.add(message);
    }
    @NonNull
    @NotNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageHolder(view, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MessageHolder holder, int position) {
        holder.bind(messageList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageHolder extends RecyclerView.ViewHolder {
        ItemMessageBinding binding;
        CircleImageView circleImageView;
        MessageFragment fragment;
        ImageView imageView;
        CardView cardView;


        public MessageHolder(@NonNull @NotNull View itemView, MessageFragment fragment) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            circleImageView = itemView.findViewById(R.id.image_message);
            imageView = itemView.findViewById(R.id.image_view);
            this.cardView = itemView.findViewById(R.id.card_view);
            this.fragment = fragment;
        }

        void bind(Message message) {
            binding.setMessage(message);
            if (message.getImage() != null) {
                BitmapService.getInstance().loadBitmap(message.getImage(), imageView);
            }
            CardView.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (message.getSender() == PreferenceService.getId(binding.getRoot().getContext())) {
                params.setMargins(150, 4, 4, 4);
                message.my = true;
                message.senderUsername = PreferenceService.getUsername(itemView.getContext());
            } else {
                params.setMargins(4, 4, 150, 4);

                message.senderUsername = fragment.getActivity().getIntent().getStringExtra("USERNAME");
                if (fragment.getActivity().getIntent().getStringExtra("IMAGE") != null) {
                    BitmapService.getInstance().loadBitmap(fragment.getActivity().getIntent().getStringExtra("IMAGE"), circleImageView);
                }
            }
            cardView.setLayoutParams(params);


        }
    }
}
