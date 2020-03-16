package com.example.dashboard.Activity.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.example.dashboard.R;

import java.util.List;

public class DoctorPagerAdapter extends FragmentStatePagerAdapter{

    private List<Fragment> fragmentList;


    public DoctorPagerAdapter(FragmentManager fragmentManager , List<Fragment> fragmentList){
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