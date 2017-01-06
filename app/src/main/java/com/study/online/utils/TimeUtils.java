package com.study.online.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roy on 2017/1/6.
 * 时间转换
 */

public class TimeUtils {
    /**
     * lang时间转换
     */
    public static String longToString(String time){
        //服务器返回时间
        long currentTime = Long.parseLong(time);
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentTime*1000);
        //指定格式的时间串
        String times = dateFormat.format(date);
        return times;
    }


}
