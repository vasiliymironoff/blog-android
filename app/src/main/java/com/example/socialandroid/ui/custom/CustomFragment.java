package com.example.socialandroid.ui.custom;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialandroid.App;
import com.example.socialandroid.R;
import com.example.socialandroid.api.model.EditProfile;
import com.example.socialandroid.api.model.ProfileDetail;
import com.example.socialandroid.databinding.CustomFragmentBinding;
import com.example.socialandroid.service.BitmapService;
import com.example.socialandroid.service.PreferenceService;
import com.example.socialandroid.ui.activity.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CustomFragment extends Fragment {

    public static final int PICK = 431;

    private CustomViewModel mViewModel;
    private CustomFragmentBinding binding;
    CircleImageView circleImageView;

    public static CustomFragment newInstance() {
        return new CustomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_fragment, container, false);
        binding = DataBindingUtil.bind(view);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Профиль");

        circleImageView = view.findViewById(R.id.person_image_edit);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.custom_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.save_profile) {
            String endImage = null;
            if (mViewModel.imageProfile != null) {
                endImage = BitmapService.getInstance().BitmapToBase64String(mViewModel.imageProfile);
            }
            EditProfile editProfile = new EditProfile(mViewModel.status.get(),
                    mViewModel.isMen.get(),
                    mViewModel.about.get(),
                    endImage,
                    null);
            Single.just(App.getService().getApi().postCustomProfile(PreferenceService.getId(getContext()), PreferenceService.getToken(getContext()), editProfile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BiConsumer<ProfileDetail, Throwable>() {
                        @Override
                        public void accept(ProfileDetail profileDetail, Throwable throwable) throws Throwable {
                            if (throwable != null) {
                                throwable.printStackTrace();
                            } else {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                    }));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CustomViewModel.class);
        binding.setModel(mViewModel);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        switch (requestCode) {
            case PICK:
                if (resultCode == Activity.RESULT_OK) {

                    try {
                        final Uri uri = data.getData();
                        final InputStream is = getActivity().getContentResolver().openInputStream(uri);
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                        mViewModel.imageProfile = bitmap;
                        circleImageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}