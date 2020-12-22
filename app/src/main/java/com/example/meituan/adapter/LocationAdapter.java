package com.example.meituan.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meituan.R;
import com.example.meituan.bean.Location;

import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ItemVH> {
    private List<Location.city> mLocationCityList;

    private static final int TYPE_GROUP = 0xa01;
    private static final int TYPE_CHILD = 0xa02;

    public void setmlocationList(List<Location.city> mCityList) {
        this.mLocationCityList = mCityList;
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
                ChildVH childVH  = (ChildVH) holder;
                childVH.tv_city.setText(c.getName());
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
}

