package com.example.meituan.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.meituan.R;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MineFragment extends Fragment {

    private View view;

    private ImageView iv_comment;
    private RelativeLayout rl_comment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment, container, false);

        iv_comment = view.findViewById(R.id.iv_comment);
        rl_comment = view.findViewById(R.id.rl_comment);
        new QBadgeView(view.getContext()).bindTarget(rl_comment).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeNumber(100).setExactMode(false).setBadgeTextSize(6,true)
                .setGravityOffset(15,6,true);
        return view;
    }
}
