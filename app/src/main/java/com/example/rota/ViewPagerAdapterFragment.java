package com.example.rota;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapterFragment extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapterFragment(FragmentManager manager) {
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

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);

        for (Fragment fragment : mFragmentList) {
            if (fragment instanceof ReelsFragment) {
                ((ReelsFragment) fragment).setUserVisibleHint(fragment == object);
            }
        }
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}