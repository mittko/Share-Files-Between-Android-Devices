package com.sendshare.movecopydata.wififiletransfer.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sendshare.movecopydata.wififiletransfer.fragments.JustFragmentFiles;
import com.sendshare.movecopydata.wififiletransfer.fragments.JustFragmentApk;

import java.util.ArrayList;

public class MyPageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    private final int fragmentNum = 5;
    public MyPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(JustFragmentApk.getInstance(0));
        for(int i = 1;i < fragmentNum;i++) {
            fragments.add(JustFragmentFiles.getInstance(i));
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragmentNum;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position + " title";
    }

    // Force a refresh of the page when a different fragment is displayed
  /*  @Override
    public int getItemPosition(Object object) {
        // this method will be called for every fragment in the ViewPager
        if (object instanceof JustFragmentFiles) {
            return POSITION_UNCHANGED; // don't force a reload
        } else {
            // POSITION_NONE means something like: this fragment is no longer valid
            // triggering the ViewPager to re-build the instance of this fragment.
            return POSITION_NONE;
        }
    }*/
    public Fragment getCurrentFragment(int page) {
        return fragments.get(page);
    }
}
