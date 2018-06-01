package com.metris.me_tris;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

public class FragmentBeranda extends Fragment {
    View view;
    private Timer timer;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private final int NUM_PAGES = 3;

    private int gambars[] = {
            R.drawable.sampul_tigawarna, R.drawable.sampul_goacina, R.drawable.sampul_balekambang
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_beranda, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager)getActivity().findViewById(R.id.viewPagerDestinasi);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                // do transformation here
                if(position <= -1.0F || position >= 1.0F) {
                    view.setTranslationX(view.getWidth() * position);
                    view.setAlpha(0.0F);
                } else if( position == 0.0F ) {
                    view.setTranslationX(view.getWidth() * position);
                    view.setAlpha(1.0F);
                } else {
                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    view.setTranslationX(view.getWidth() * -position);
                    view.setAlpha(1.0F - Math.abs(position));
                }
            }
        });
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), gambars);
        viewPager.setAdapter(viewPagerAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        if(viewPager.getCurrentItem() == NUM_PAGES - 1){
                            viewPager.setCurrentItem(0);
                        } else {
                            viewPager.setCurrentItem((viewPager.getCurrentItem()+1));
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 6000, 6000);
    }


}