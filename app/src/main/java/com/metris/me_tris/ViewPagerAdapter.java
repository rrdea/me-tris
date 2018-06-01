package com.metris.me_tris;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter{

    Activity activity;
    String[] gambar;
    LayoutInflater inflater;

    public ViewPagerAdapter(Activity activity, String[] gambar) {
        this.activity = activity;
        this.gambar = gambar;
    }

    @Override
    public int getCount() {
        return gambar.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
