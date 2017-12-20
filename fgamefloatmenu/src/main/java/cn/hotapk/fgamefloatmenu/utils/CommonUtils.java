package cn.hotapk.fgamefloatmenu.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * @author laijian
 * @version 2017/12/20
 * @Copyright (C)上午12:04 , www.hotapk.cn
 */
public class CommonUtils {


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context,float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获得状态栏的高度pix
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     * @param activity
     * @return 判断当前手机是否是全屏
     */
    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        if ((flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return true;
        } else {
            return false;
        }
    }

}
