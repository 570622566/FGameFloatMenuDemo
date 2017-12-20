package cn.hotapk.fgamefloatmenu;

import android.app.Activity;
import android.app.Application;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.hotapk.fgamefloatmenu.utils.CommonUtils;

/**
 * @author laijian
 * @version 2017/12/15
 * @Copyright (C)下午4:42 , www.hotapk.cn
 */
public class FFloatMenu {

    /**
     * 来自 activity 的 wManager
     */
    private WindowManager wManager;

    /**
     * 为 wManager 设置 LayoutParams
     */
    private WindowManager.LayoutParams wmParams;

    /**
     * 用于显示在 mActivity 上的 mActivity
     */
    private Activity mActivity;

    private LinearLayout logoLay;

    private ImageView floatMenu;

    private FFloatMenuView fFloatMenuView;

    private FFloatMenuBuilder fFloatMenuBuilder;

    private OnMenuClickListener onMenuClickListener;

    private int mScreenWidth;

    private int oldX = 0;

    private int oldY = 0;

    private boolean showMenu = false;

    private boolean isDestroy = false;
    private boolean isHide = false;

    public FFloatMenu(FFloatMenuBuilder fFloatMenuBuilder) {
        this.fFloatMenuBuilder = fFloatMenuBuilder;
        mActivity = this.fFloatMenuBuilder.getmActivity();
        creatView();
        initFloatWindow();
        activityLifecycle();
    }

    /**
     * 创建悬浮窗的view
     */
    private void creatView() {
        LinearLayout.LayoutParams logoLayParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams floatMenuParams = new LinearLayout.LayoutParams(CommonUtils.dip2px(mActivity, fFloatMenuBuilder.getLogoWdith()), CommonUtils.dip2px(mActivity, fFloatMenuBuilder.getLogoWdith()));
        logoLay = new LinearLayout(mActivity);
        floatMenu = new ImageView(mActivity);
        logoLay.setLayoutParams(logoLayParams);
        floatMenu.setLayoutParams(floatMenuParams);
        floatMenu.setBackgroundResource(fFloatMenuBuilder.getLogoRes());
        logoLay.setOnTouchListener(touchListener);
        logoLay.addView(floatMenu);
        fFloatMenuView = new FFloatMenuView.Builder()
                .setBuilder(fFloatMenuBuilder)
                .setOnItemClickListener(new FFloatMenuView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (onMenuClickListener != null) {
                            onMenuClickListener.onItemClick(position);
                        }
                    }

                    @Override
                    public void dismiss() {
                        hideMenu();
                        if (onMenuClickListener != null) {
                            onMenuClickListener.dismiss();
                        }
                    }
                })
                .creat();

    }


    /**
     * 初始化悬浮球 window
     */
    private void initFloatWindow() {
        wManager = mActivity.getWindowManager();
        mScreenWidth = wManager.getDefaultDisplay().getWidth();
        wmParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN
                , PixelFormat.RGBA_8888);
        wmParams.gravity = Gravity.START | Gravity.TOP;
        wmParams.x = 0;
        wmParams.alpha = 0.2f;//透明度
        wmParams.y = CommonUtils.isFullScreen(mActivity) ? 0 : CommonUtils.getStatusHeight(mActivity);
        try {
            wManager.addView(logoLay, wmParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 这个事件用于处理移动、自定义点击或者其它事情，return true可以保证onclick事件失效
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    oldX = (int) event.getRawX();
                    oldY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!showMenu) {
                        wmParams.x = (int) event.getRawX();
                        wmParams.y = (int) event.getRawY();
                        wManager.updateViewLayout(logoLay, wmParams);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs((int) event.getRawX() - oldX) < 10 && Math.abs((int) event.getRawY() - oldY) < 10) {
                        wManager.removeViewImmediate(logoLay);
                        if (Math.abs(mScreenWidth - wmParams.x) < 20) {
                            fFloatMenuView.showMenuLeft();
                        } else {
                            fFloatMenuView.showMenuRight();
                        }
                        wManager.addView(fFloatMenuView, wmParams);
                        oldX = (int) event.getRawX();
                        oldY = (int) event.getRawY();
                        if (oldX > mScreenWidth / 2) {
                            wmParams.x = mScreenWidth;
                            wManager.updateViewLayout(fFloatMenuView, wmParams);
                        }
                        showMenu = !showMenu;
                    } else {
                        if (!showMenu) {
                            oldX = (int) event.getRawX();
                            oldY = (int) event.getRawY();
                            if (oldX > mScreenWidth / 2) {
                                wmParams.x = mScreenWidth;
                            } else {
                                wmParams.x = 0;
                            }
                            if (!CommonUtils.isFullScreen(mActivity)) {
                                if (wmParams.y < CommonUtils.getStatusHeight(mActivity)) {
                                    wmParams.y = CommonUtils.getStatusHeight(mActivity);
                                }
                            }
                            wManager.updateViewLayout(logoLay, wmParams);
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:

                    break;
            }
            return true;
        }
    };

    /**
     * 设置事件监听
     *
     * @param onMenuClickListener
     */
    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }


    /**
     * 显示悬浮窗菜单栏
     */
    public void showMenu() {

    }

    /**
     * 隐藏悬浮窗菜单栏
     */

    public void hideMenu() {
        if (showMenu) {
            wManager.removeViewImmediate(fFloatMenuView);
            wManager.addView(logoLay, wmParams);
            showMenu = !showMenu;
        }
    }

    /**
     * 显示悬浮窗
     */
    public void showFloat() {
        if (!isHide || isDestroy) {
            return;
        }
        try {
            wManager.addView(logoLay, wmParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showMenu = false;
        isHide = false;
    }

    /**
     * 隐藏悬浮窗
     */
    public void hideFloat() {
        if (isDestroy || isHide) {
            return;
        }
        try {
            if (showMenu) {
                wManager.removeViewImmediate(fFloatMenuView);
            } else {
                wManager.removeViewImmediate(logoLay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isHide = true;
    }


    /**
     * 移除悬浮窗 释放资源
     */
    private void destroyFloat() {
        try {

            hideFloat();
            fFloatMenuView.clearViews();
            mActivity = null;
            wManager = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface OnMenuClickListener {

        void onItemClick(int position);

        void dismiss();

        void open();
    }

    /**
     * activity 生命周期监听
     */
    private void activityLifecycle() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return;
        }
        ((Application) mActivity.getApplicationContext()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (mActivity != null) {
                    if (mActivity == activity) {
                        if (wManager != null) {
                            destroyFloat();
                            isDestroy = true;
                        }
                    }
                }

            }
        });
    }
}
