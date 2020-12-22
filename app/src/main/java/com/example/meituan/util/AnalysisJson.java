package com.example.meituan.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.meituan.bean.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 封装了IO流读取assets目录下的json文件和Gson解析json的工具类
 */
public class AnalysisJson {

    /**
     * 解析json文件，返回对象类型数据
     * @param context
     * @param fileName
     * @param mclass
     * @return Object
     */
    public static Object analysisJson(Context context, String fileName, Object mclass) {
        return parseJSON(getJson(context, fileName), mclass);
    }

    /**
     * 解析json文件，返回对象数组类型数据
     * @param context
     * @param fileName
     * @return List<Object>
     */
    public static List<Location.city> analysisListJson(Context context, String fileName) {
        return parseListJSON(getJson(context, fileName));
    }

    /**
     * 得到assets目录下的json文件中的内容转换成字符串类型
     * @param context
     * @param fileName
     * @return String
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * GSON解析json字符串转换成Object类型
     * @param jsonData
     * @return Object
     */
    public static Object parseJSON(String jsonData, Object  mclass) {
        Log.d("parseJSON", String.valueOf(mclass.getClass()));
        return new Gson().fromJson(jsonData, mclass.getClass());
    }

    /**
     * GSON解析json字符串转换成List<Location.city>类型
     * @param jsonData
     * @return List<Object>
     */
    public static List<Location.city> parseListJSON(String jsonData) {
        return new Gson().fromJson(jsonData, new TypeToken<List<Location.city>>(){}.getType());
    }

}
