package com.metris.me_tris;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class FragmentAkun  extends Fragment{






        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_trip,container, false);
            ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPagerTrip);
            setupViewPager(viewPager);
            TabLayout tabs = (TabLayout) view.findViewById(R.id.tabTrip);
            tabs.setupWithViewPager(viewPager);

            return view;

        }

        private void setupViewPager(ViewPager viewPager) {

            com.metris.me_tris.FragmentTrip.Adapter adapter = new com.metris.me_tris.FragmentTrip.Adapter(getChildFragmentManager());
            adapter.addFragment(new FragmentTrip_Mendatang(), "Mendatang");
            adapter.addFragment(new FragmentTrip_Sebelumnya(), "Sebelumnya");
            viewPager.setAdapter(adapter);

        }

        static class Adapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public Adapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }


    }

