package com.example.reminder;

import android.util.Log;

import com.example.reminder.Database.MyList;
import com.example.reminder.Fragment.First;
import com.example.reminder.Fragment.Second;
import com.example.reminder.Fragment.Third;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tabsNumber;
    ArrayList<MyList> arr;


    public PagerAdapter(@NonNull FragmentManager fm, int behavior,int tabs,ArrayList<MyList> arr) {
        super(fm, behavior);
        this.tabsNumber = tabs;
        this.arr = arr;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Log.i("TAG", "Pager Adapter "+String.valueOf(arr.size()));
                return new First(arr);
            case 1:
                return new Second(arr);
            case 2:
                return new Third(arr);

                default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }

}
