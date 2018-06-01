package com.metris.me_tris;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityBeranda extends AppCompatActivity {

    private Timer timer;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private final int NUM_PAGES = 3;

    private int gambars[] = {
            R.drawable.sampul_tigawarna, R.drawable.sampul_goacina, R.drawable.sampul_balekambang
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        viewPager = (ViewPager)findViewById(R.id.viewPagerDestinasi);
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
        viewPagerAdapter = new ViewPagerAdapter(this, gambars);
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

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}
