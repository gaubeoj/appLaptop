package com.nhom5.quanlylaptop.FragmentLaptop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhom5.quanlylaptop.Entity.Photo;
import com.nhom5.quanlylaptop.KH_Loader.KH_Laptop_Loader;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class LaptopAsusFragment extends Fragment {

    List<Photo> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laptop_asus, container, false);
        SliderView sliderView = view.findViewById(R.id.sliderView);

        list = setListPhoto();
        SliderAdapter sliderAdapter = new SliderAdapter(list);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        KH_Laptop_Loader kh_laptop_loader = new KH_Laptop_Loader(getContext(), view.findViewById(R.id.recyclerView_Laptop_Asus));
        kh_laptop_loader.execute("LAsus");

        return view;
    }

    private List<Photo> setListPhoto() {
        list.add(new Photo(R.drawable.img_laptop_asus));
        list.add(new Photo(R.drawable.ar1));
        list.add(new Photo(R.drawable.ar2));
        list.add(new Photo(R.drawable.ar3));
        list.add(new Photo(R.drawable.ar4));
        return list;
    }
}