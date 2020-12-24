package com.example.meituan.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituan.R;

import com.example.meituan.bean.Location;

import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ItemVH> {
    private List<Location.city> mLocationCityList;
    private List<Location.hotCity> mLocationHotCityList;
    Activity activity;

    private static final int TYPE_GROUP = 0xa01;
    private static final int TYPE_CHILD = 0xa02;
    private static final int TYPE_GROUP_A = 0xa03;

    public void setmlocationList(List<Location.city> mCityList) {
        this.mLocationCityList = mCityList;
    }

    public void setmlocationHotList(List<Location.hotCity> mHotCityList) {
        this.mLocationHotCityList = mHotCityList;
    }

    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        ItemVH itemVH = null;
        switch (viewType) {
            case TYPE_GROUP:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_group_view, parent, false);
                itemVH = new GroupVH(view);
                break;
            case TYPE_CHILD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_view, parent, false);
                itemVH = new ChildVH(view);
                break;
            case TYPE_GROUP_A:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_a, parent, false);
                itemVH = new GroupAVH(view);
        }

        return itemVH;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ItemVH holder, int position) {

        Location.city city = mLocationCityList.get(position);
        switch (getItemViewType(position)) {
            case TYPE_GROUP:
                Group g = (Group) city;
                GroupVH groupVH = (GroupVH) holder;
                groupVH.tv_group.setText(g.getPinyin());
                break;
            case TYPE_CHILD:
                Child c = (Child) city;
                ChildVH childVH = (ChildVH) holder;
                childVH.tv_city.setText(c.getName());
                break;
            case TYPE_GROUP_A:
                GroupAVH groupAVH = (GroupAVH) holder;
                int cur = 0;
                activity = (Activity) holder.itemView.getContext();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Button btn = new Button(activity);
                        btn.setBackground(activity.getResources().getDrawable(R.drawable.bg_white_btn));
                        btn.setText(mLocationHotCityList.get(cur++).getName());
                        GridLayout.Spec rowSpec = GridLayout.spec(i);
                        GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.width = 280;
                        params.height = 120;
                        params.topMargin = 40;
                        params.leftMargin = 40;
                        groupAVH.gridLayout.addView(btn, params);
                    }
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mLocationCityList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mLocationCityList.get(position).getType();
    }

    /**
     * 提供给Activity刷新数据
     * @param list
     */
    public void updateList(List<Location.city> list){
        this.mLocationCityList = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mLocationCityList.get(position);
    }

    /**
     * Recycle分组，group是组头
     */
    public static class Group extends Location.city {

        @Override
        public int getType() {
            return TYPE_GROUP;
        }
    }

    public static class Child extends Location.city {

        @Override
        public int getType() {
            return TYPE_CHILD;
        }

    }

    public static class GroupA extends Location.city {

        @Override
        public int getType() {
            return TYPE_GROUP_A;
        }
    }

    private static class GroupAVH extends ItemVH {

        LinearLayout ll_city_A;
        GridLayout gridLayout;

        public GroupAVH(View itemView) {
            super(itemView);
            ll_city_A = itemView.findViewById(R.id.ll_city_A);
            gridLayout = itemView.findViewById(R.id.gl_hot_city_grid);
        }

        @Override
        public int getType() {
            return TYPE_GROUP;
        }
    }


    private static class GroupVH extends ItemVH {

        TextView tv_group;

        public GroupVH(View itemView) {
            super(itemView);
            tv_group = itemView.findViewById(R.id.tv_group);
        }

        @Override
        public int getType() {
            return TYPE_GROUP;
        }
    }

    private static class ChildVH extends ItemVH {

        TextView tv_city;

        public ChildVH(View itemView) {
            super(itemView);
            tv_city = itemView.findViewById(R.id.tv_city);
        }

        @Override
        public int getType() {
            return TYPE_CHILD;
        }
    }

    public static abstract class ItemVH extends RecyclerView.ViewHolder {
        public ItemVH(View itemView) {
            super(itemView);
        }

        public abstract int getType();
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mLocationCityList.get(position).getPinyin().charAt(0) - 32;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mLocationCityList.get(i).getPinyin().substring(0, 1);
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }

}