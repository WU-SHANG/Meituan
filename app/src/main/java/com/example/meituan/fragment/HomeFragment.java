package com.example.meituan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.meituan.R;
import com.example.meituan.activity.LocationActivity;
import com.example.meituan.activity.MainActivity;
import com.example.meituan.adapter.MyGridViewAdapter;
import com.example.meituan.adapter.MyPagerAdapter;
import com.example.meituan.adapter.MyViewPagerAdapter;
import com.example.meituan.bean.ProductListBean;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {
    /**
     * 地理位置
     */
    private RelativeLayout rl_location;
    /**
     * 页面头部滑动导航栏
     */
    private View view;  //this碎片的view
    private ViewGroup points;//小圆点指示器
    private ImageView[] ivPoints;//小圆点图片集合
    private ViewPager viewPager;
    private int totalPage;//总的页数
    private int mPageSize = 8;//每页显示的最大数量
    private List<ProductListBean> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private int currentPage;//当前页
    /**
     * 中间部分滑动导航栏
     */
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] proName = {"名称0","名称1","名称2","名称3","名称4","名称5",
            "名称6","名称7","名称8","名称9","名称10","名称11","名称12","名称13",
            "名称14","名称15","名称16","名称17","名称18","名称19"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        iniViews();
        setDatas();

        setTopViewPager(inflater);
        setMiddleViewPager();


        return view;
    }

    /**
     * 初始化控件
     */
    private void iniViews() {
        //上方的viewpager
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //初始化小圆点指示器
        points = (ViewGroup) view.findViewById(R.id.points);

        //中间的viewpager
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLayout = view.findViewById(R.id.tab_layout);
        rl_location = view.findViewById(R.id.rl_location);
        rl_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), LocationActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 模拟数据源
     */
    private void setDatas() {
        listDatas = new ArrayList<>();
        for (String s : proName) {
            listDatas.add(new ProductListBean(s, R.drawable.img));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //改变小圆圈指示器的切换效果
        setImageBackground(position);
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 改变点点点的切换效果
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < ivPoints.length; i++) {
            if (i == selectItems) {
                ivPoints[i].setBackgroundResource(R.drawable.page__selected_indicator);
            } else {
                ivPoints[i].setBackgroundResource(R.drawable.page__normal_indicator);
            }
        }
    }

    private void setTopViewPager(LayoutInflater inflater) {
        //总的页数，取整（这里有三种类型：Math.ceil(3.5)=4:向上取整，只要有小数都+1  Math.floor(3.5)=3：向下取整  Math.round(3.5)=4:四舍五入）
        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for(int i=0;i<totalPage;i++){
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.viewpager_item_view,viewPager,false);
            gridView.setAdapter(new MyGridViewAdapter(view.getContext(),listDatas,i,mPageSize));
            //添加item点击监听
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + currentPage*mPageSize;
                    Log.i("TAG","position的值为："+position + "-->pos的值为："+pos);
                    Toast.makeText(view.getContext(),"你点击了 "+listDatas.get(pos).getProName(),Toast.LENGTH_SHORT).show();
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        viewPager.setAdapter(new MyViewPagerAdapter(viewPagerList));
        //小圆点指示器
        ivPoints = new ImageView[totalPage];
        for(int i=0;i<ivPoints.length;i++){
            ImageView imageView = new ImageView(view.getContext());
            //设置图片的宽高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            if(i == 0){
                imageView.setBackgroundResource(R.drawable.page__selected_indicator);
            }else{
                imageView.setBackgroundResource(R.drawable.page__normal_indicator);
            }
            ivPoints[i] = imageView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 20;//设置点点点view的左边距
            layoutParams.rightMargin = 20;//设置点点点view的右边距
            points.addView(imageView,layoutParams);
        }
        //设置ViewPager滑动监听
        viewPager.addOnPageChangeListener(this);
    }

    private void setMiddleViewPager() {
        final PagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(), mTabLayout.getTabCount());
        //ViewPager设置Adapter
        mViewPager.setAdapter(adapter);

        //为ViewPager添加页面改变监听
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        //为TabLayout添加Tab选择监听

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}

