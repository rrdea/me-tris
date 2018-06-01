package com.metris.me_tris;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter{

    private Activity activity;
    private String[] gambars;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Activity activity, String[] gambar) {
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
        View itemView = inflater.inflate(R.layout.viewpager_item, container,false);

        ImageView gambar;
        gambar = (ImageView)itemView.findViewById(R.id.iv_slider_destinasi);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        gambar.setMinimumHeight(height);
        gambar.setMinimumWidth(width);

        try{
            Picasso.with(activity.getApplicationContext())
                    .load(gambars[position])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(gambar);
        } catch (Exception ex){}

        container.addView(itemView);
        return itemView;

    }
}
