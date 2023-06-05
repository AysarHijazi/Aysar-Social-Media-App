package com.example.rota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = findViewById(R.id.vieqpager);
        bottomNavigationView = findViewById(R.id.bottomnavigation);

        ViewPagerAdapterFragment viewPagerAdapterFragment = new ViewPagerAdapterFragment(getSupportFragmentManager());
        viewPagerAdapterFragment.addFragment(new BlankFragment1());
        viewPagerAdapterFragment.addFragment(new BlankFragment2());
        viewPagerAdapterFragment.addFragment(new ReelsFragment());

        viewPagerAdapterFragment.addFragment(new BlankFragment3());
        viewPagerAdapterFragment.addFragment(new BlankFragment4());
        viewPager.setAdapter(viewPagerAdapterFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.home) {
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.User) {
                    viewPager.setCurrentItem(1);
                } else if (id == R.id.Reels) {
                    viewPager.setCurrentItem(2);
                } else if (id == R.id.Chat){
                    viewPager.setCurrentItem(3);
                }
                else {
                    viewPager.setCurrentItem(4);
                }

                return true;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                bottomNavigationView.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}