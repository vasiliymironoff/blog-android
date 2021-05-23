package com.example.socialandroid.service;

import android.graphics.Bitmap;

import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BitmapService {

    private static BitmapService service;
    private LruCache<String, Bitmap> lruCache;


    private BitmapService() {
    }


    public static BitmapService getInstance() {
        if (service == null) {
            service = new BitmapService();
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            service.lruCache = new LruCache<String, Bitmap>(maxMemory) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() / 1024;
                }
            };
        }
        return service;
    }

    public void addBitmap(String key, Bitmap bitmap) {
        Log.d("tag", "saveBitmap");
        lruCache.put(key, bitmap);

    }

    public Bitmap getBitmap(String key) {
        Log.d("tag", "get");
        return lruCache.get(key);
    }


    public void loadBitmap(String path, ImageView imageView) {
        Single.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                if (getBitmap(path) == null) {
                    Log.d("tag", "save");
                    Bitmap bitmap = Picasso.get().load(path).get();
                    addBitmap(path, bitmap);
                    return bitmap;
                } else {
                    Log.d("tag", "Уже была");
                    return getBitmap(path);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Bitmap, Throwable>() {
                    @Override
                    public void accept(Bitmap bitmap, Throwable throwable) throws Throwable {
                        if (throwable != null) {
                            throwable.printStackTrace();
                        } else {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
    }
}
