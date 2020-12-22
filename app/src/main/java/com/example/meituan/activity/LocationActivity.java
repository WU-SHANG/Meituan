package com.example.meituan.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.meituan.R;
import com.example.meituan.adapter.LocationAdapter;
import com.example.meituan.bean.Location;
import com.example.meituan.util.AnalysisJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationActivity";

    private Location location;
    /**
     * 城市列表，第一个是解析json得到的，第二个是排序后的
     */
    private List<Location.city> locationCityList = new ArrayList<>();
    private List<Location.city> mlocationCityList = new ArrayList<>();

    RecyclerView recyclerView;

    private LocationAdapter locationAdapter = new LocationAdapter();

    private List<String> letters = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    private GridLayout gridLayout;

    private List<Location.hotCity> locationHotCityList = new ArrayList<>();

    /**
     * 定位，rl是整个按钮布局，tv是省份文字
     */
    private RelativeLayout rl_current_location;
    private TextView tv_current_location;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        initView();

        //读取json文件
        String fileName = "cities.json";
        location = (Location) AnalysisJson.analysisJson(this, fileName, new Location());
        Log.d("location", String.valueOf(location.getPos()));

        loadHotCityGrid();

        loadCityRecycle();

        getCurrentPosition();


    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_location_view);
        gridLayout = findViewById(R.id.gl_hot_city_grid);
        rl_current_location = findViewById(R.id.rl_current_location);
        tv_current_location = findViewById(R.id.tv_current_location);
    }

    /**
     * 城市列表按首字母A-Z排序
     */
    private void citySort() {
        for (int i = 0; i < letters.size(); i++) {
            String str = letters.get(i);
            char ch = (char) (str.charAt(0) + 32);
            LocationAdapter.Group group = new LocationAdapter.Group();
            group.setPinyin(letters.get(i));
            mlocationCityList.add(group);
            for (Location.city city : locationCityList) {
                char c = city.getPinyin().charAt(0);
                if (ch == c) {
                    LocationAdapter.Child child = new LocationAdapter.Child();
                    child.setId(city.getId());
                    child.setName(city.getName());
                    child.setRank(city.getRank());
                    child.setPinyin(city.getPinyin());
                    child.setLng(city.getLng());
                    child.setLat(city.getLat());
                    child.setDivisionStr(city.getDivisionStr());
                    child.setOpen(city.isOpen());
                    child.setWeather(city.isWeather());
                    mlocationCityList.add(child);
                }
            }
        }

    }

    /**
     * 加载热门城市网格布局
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void loadHotCityGrid() {
        locationHotCityList = location.getHotCityList();
        int cur = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button(this);
                btn.setBackground(getResources().getDrawable(R.drawable.bg_white_btn));
                btn.setText(locationHotCityList.get(cur++).getName());
                GridLayout.Spec rowSpec = GridLayout.spec(i);
                GridLayout.Spec columnSpec = GridLayout.spec(j);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                params.width = 280;
                params.height = 120;
                params.topMargin = 40;
                params.leftMargin = 40;
                gridLayout.addView(btn, params);
            }
        }

    }

    /**
     * 加载城市列表数据
     */
    private void loadCityRecycle() {

        locationCityList = location.getCityList();
        Log.d("locationCityList", locationCityList.get(0).getName());

        citySort();

        //需要context对象参数
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        locationAdapter.setmlocationList(mlocationCityList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(locationAdapter);

        Log.d(TAG, String.valueOf(locationAdapter.getItemCount()));

    }

    /**
     * 注册定位监听器，获取定位需要的权限
     */
    private void getCurrentPosition() {
        mLocationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        //需要获取当前位置详细的地址信息
        option.setIsNeedAddress(true);
        //设置刷新频率5秒一次
        //option.setScanSpan(5000);
        //设置定位模式为GPS定位
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new MyLocationListener());
        //权限列表
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        mLocationClient.start();
    }

    /**
     * 结束时手动关闭LocationClient来停止定位，不然会在后台持续的定位，严重消耗手机电量
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            runOnUiThread(() -> {
                String currentPosition = location.getCity();
                tv_current_location.setText(currentPosition);
            });
        }

    }
}



