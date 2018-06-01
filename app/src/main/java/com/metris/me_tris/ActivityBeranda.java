package com.metris.me_tris;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityBeranda extends AppCompatActivity {

    private Timer timer;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private final int NUM_PAGES = 2;

    private String gambars[] = {
            "https://3.bp.blogspot.com/-79bvqkxoilM/WgWnsejwYCI/AAAAAAAAAgw/aEC86BU9Xd8Fax97SBSn3s2476aSoWr_gCPcBGAYYCw/s1600/d68dcd917e03da9cf85d1a8b21fa5654-compressed.jpg",
            "https://1.bp.blogspot.com/-M2yZAmQJGvI/WgWRu7JWbEI/AAAAAAAAAgM/K2DxKEuOEIkpS6pY7sIlvP0sdYB9zA2HACLcBGAs/s1600/pantai-3-warna-malang-by-innokribow-compressed.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        viewPager = (ViewPager)findViewById(R.id.viewPagerDestinasi);
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
        timer.schedule(timerTask, 3000, 3000);

    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}
