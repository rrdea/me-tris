package com.metris.me_tris;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter{

    private Activity activity;
    private int[] gambars;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Activity activity, int[] gambar) {
        this.activity = activity;
        this.gambars = gambar;
    }

    @Override
    public int getCount() {
        return gambars.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewItem = inflater.inflate(R.layout.viewpager_item, container,false);

        ImageView imageView = (ImageView) viewItem.findViewById(R.id.iv_slider_destinasi);
        imageView.setImageResource(gambars[position]);
        ((ViewPager)container).addView(viewItem);

        return viewItem;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }
}
