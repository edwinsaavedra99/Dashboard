package com.example.dashboard.Activity.Study;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class StudyPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;


    public StudyPagerAdapter(FragmentManager fragmentManager , List<Fragment> fragmentList){
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }

}
