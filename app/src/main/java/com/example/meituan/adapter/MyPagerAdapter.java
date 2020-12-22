package com.example.meituan.adapter;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.meituan.fragment.BargainFragment;
import com.example.meituan.fragment.DiscoverFragment;
import com.example.meituan.fragment.GoodsFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
    //fragment的数量
    int nNumOfTabs;
    public MyPagerAdapter(FragmentManager fm, int nNumOfTabs)
    {
        super(fm);
        this.nNumOfTabs=nNumOfTabs;
    }

    /**
     * 重写getItem方法
     *
     * @param position 指定的位置
     * @return 特定的Fragment
     */
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                GoodsFragment tab1=new GoodsFragment();
                return tab1;
            case 1:
                BargainFragment tab2=new BargainFragment();
                return tab2;
            case 2:
                DiscoverFragment tab3=new DiscoverFragment();
                return tab3;
        }
        return null;
    }

    /**
     * 重写getCount方法
     *
     * @return fragment的数量
     */
    @Override
    public int getCount() {
        return nNumOfTabs;
    }


}
