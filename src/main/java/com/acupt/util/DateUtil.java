package com.acupt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liujie on 2017/8/14.
 */
public class DateUtil {

    private static ThreadLocal<SimpleDateFormat> dateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String formatToDateTime(Date date) {
        return dateTimeFormat.get().format(date);
    }

    public static Date newDate(long time) {
        if (time > 1000000000000L) {
            return new Date(time);
        }
        return new Date(time * 1000);
    }
}
