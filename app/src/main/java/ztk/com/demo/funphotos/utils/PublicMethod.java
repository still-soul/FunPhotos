package ztk.com.demo.funphotos.utils;
import android.content.Context;
import android.content.res.Resources;
/**
 * @author Administrator
 */
public class PublicMethod {

    /**
     * dip to px
     * @param dipValue
     * @return
     */
    public static int dip2px( float dipValue) {
        float scale =Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取去掉边距后的屏幕宽度
     * @param context
     * @param margin 边距值
     * @return
     */
    public static int getScreenWidth(Context context, int margin) {
        if (context == null) {
            return 0;
        }

        int screenWidth = getScreenWidth(context);
        if (screenWidth > 0) {
            screenWidth -= 2 * margin;
        }
        return screenWidth;
    }
    /**
     * get device width
     * @param context
     */
    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }

        return context.getResources().getDisplayMetrics().widthPixels;
    }



}
