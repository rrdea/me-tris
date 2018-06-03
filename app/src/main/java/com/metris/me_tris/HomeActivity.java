package com.metris.me_tris;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_beranda:
                    loadFragment(new FragmentBeranda());
                    return true;
                case R.id.navigation_tripsaya:
                    //loadFragment(new FragmentTrip());
                    return true;
                case R.id.navigation_pakettrip:
                    //loadFragment(new FragmentPaket());
                    return true;
                case R.id.navigation_akunsaya:
                    //loadFragment(new FragmentAkun());
                    return true;
                case R.id.navigation_lainnya:
                    loadFragment(new FragmentLainnya());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_beranda);

        loadFragment(new FragmentBeranda());
    }

    private void loadFragment(Fragment fragment) {

        // create a FragmentManager
        FragmentManager fm = getFragmentManager();


        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fl_beranda, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
