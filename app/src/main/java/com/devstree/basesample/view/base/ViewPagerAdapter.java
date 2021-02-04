package com.devstree.basesample.view.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.devstree.foodguru.common.BaseFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<BaseFragment> fragments;

    public ViewPagerAdapter(FragmentActivity fragmentActivity, List<BaseFragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }


    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
