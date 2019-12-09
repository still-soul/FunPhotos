package ztk.com.demo.funphotos.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符操作类
 */
public class StringUtils {

    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

}
