package com.nhom5.quanlylaptop.PagerAdapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.nhom5.quanlylaptop.Entity.Photo;
import com.nhom5.quanlylaptop.R;

import java.util.List;
import java.util.logging.ConsoleHandler;

public class Photo_Adapter extends PagerAdapter {
    Context context;
    List<Photo> listPhoto;
    ImageView imgphoto;
    int pos;
    Handler handler;

    public Photo_Adapter(Context context, List<Photo> listPhoto) {
        this.context = context;
        this.listPhoto = listPhoto;
        handler = new Handler();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.cardview_photo, container, false);
        imgphoto = view.findViewById(R.id.imageView_Laptop);
        changeBackG.run();
        this.pos = position;
        Photo photo = listPhoto.get(position);
        if (photo != null) {
            Glide.with(context).load(photo.getResurceId()).into(imgphoto);
        }
        container.addView(view);
        return view;
    }

    private final Runnable changeBackG = new Runnable() {
        public void run() {
            if (pos < (listPhoto.size() - 1)){
                pos += 1;
            } else {
                pos = 0;
            }
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    public int getCount() {
        if (listPhoto != null) {
            return listPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
