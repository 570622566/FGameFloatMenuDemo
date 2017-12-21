package cn.hotapk.fgamefloatmenu;

import android.app.Activity;
import android.app.Application;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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
     * 悬浮窗显示在左边
     */
    public static final int SHOW_LEFT = 1;

    /**
     * 悬浮窗显示在右边
     */
    public static final int SHOW_RIGHT = 2;

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

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 记录按下时的X坐标
     */
    private int oldX = 0;

    /**
     * 记录按下时的Y坐标
     */
    private int oldY = 0;

    /**
     * 是否显示了菜单栏
     */
    private boolean showMenu = false;

    /**
     * 清理资源
     */
    private boolean isDestroy = false;

    /**
     * 隐藏悬浮窗
     */
    private boolean isHide = false;

    /**
     * 刷新悬浮窗的频率
     */
    private int updataViewTime = 0;

    /**
     * 初始化默认显示在左上角
     */
    private int defPositionShow = SHOW_LEFT;

    /**
     * 初始化默认Y坐标的偏移量
     */
    private int defOffsetY = 70;

    /**
     * 靠边缩小时透明度
     */
    private float viewAlpha = 1f;

    /**
     * 倒计时后，半隐藏悬浮球移动x的距离
     */
    private int logoScrollX;

    /**
     * 倒计时 半隐藏悬浮球logo定时器
     */
    private CountDownTimer countDownTimer;

    /**
     * 半隐藏悬浮球倒计时 秒
     */
    private int millisInFuture = 6;

    /**
     * 是否正在移动悬浮球
     */
    private boolean ismove = false;
    /**
     * logo旋转动画
     */
    private boolean rotateLogo = false;

    private boolean hasAnimation = false;

    /**
     * 拖拽点是否在悬浮球中心点
     */
    private boolean centerInLogo = true;


    private RotateAnimation rotateAnimation;

    public FFloatMenu(FFloatMenuBuilder fFloatMenuBuilder) {
        this.fFloatMenuBuilder = fFloatMenuBuilder;
        mActivity = this.fFloatMenuBuilder.getmActivity();
        defPositionShow = this.fFloatMenuBuilder.getDefPositionShow();
        defOffsetY = this.fFloatMenuBuilder.getDefOffsetY();
        viewAlpha = this.fFloatMenuBuilder.getViewAlpha();
        rotateLogo = this.fFloatMenuBuilder.isRotateLogo();
        centerInLogo = this.fFloatMenuBuilder.isCenterInLogo();
        millisInFuture = this.fFloatMenuBuilder.getMillisInFuture();
        logoScrollX = this.fFloatMenuBuilder.getHideLogoSize() > 0 ? CommonUtils.dip2px(mActivity,  this.fFloatMenuBuilder.getHideLogoSize()):
                CommonUtils.dip2px(mActivity, fFloatMenuBuilder.getLogoWdith()) / 2;
        creatView();
        initFloatWindow();
        activityLifecycle();
        initTimer();
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
        if (rotateLogo) {
            rotateAnimation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(1000);
            rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
            rotateAnimation.setInterpolator(new AccelerateInterpolator()); // 设置插入器
        }
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
        wmParams.x = defPositionShow == SHOW_LEFT ? 0 : mScreenWidth;
        wmParams.y = CommonUtils.dip2px(mActivity, defOffsetY);
        try {
            wManager.addView(logoLay, wmParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initTimer() {
        countDownTimer = new CountDownTimer(millisInFuture * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (ismove || showMenu) {
                    timeCancel();
                } else {
                    if (millisUntilFinished / 1000 == millisInFuture / 2) {
                        wmParams.alpha = viewAlpha;
                        try {
                            wManager.updateViewLayout(logoLay, wmParams);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
                System.out.println("完成");
                if (!ismove && !showMenu) {
                    if (defPositionShow == SHOW_LEFT) {
                        logoLay.setScrollX(logoScrollX);
                    } else {
                        logoLay.setScrollX(-logoScrollX);
                    }
                    try {
                        wManager.updateViewLayout(logoLay, wmParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    countDownTimer.cancel();
                }
            }
        };
        countDownTimer.start();
    }

    /**
     * 取消倒计时
     */
    private void timeCancel() {
        countDownTimer.cancel();
        logoLay.setScrollX(0);
        wmParams.alpha = 1;
    }

    /**
     * 这个事件用于处理移动、自定义点击或者其它事情，return true可以保证onclick事件失效
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    eventDown(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    enentMove(event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    eventUp(event);
                    break;
            }
            return true;
        }
    };

    /**
     * down事件
     */

    private void eventDown(MotionEvent event) {
        oldX = (int) event.getRawX();
        oldY = (int) event.getRawY();
        if (!ismove) {
            logoLay.setScrollX(0);
            wmParams.alpha = 1;
            wManager.updateViewLayout(logoLay, wmParams);
        }
        ismove = true;
    }

    /**
     * 移动事件
     *
     * @param event
     */
    private void enentMove(MotionEvent event) {

        if (!showMenu) {
            updataViewTime++;
            if (updataViewTime > 2) {
                wmParams.x = centerInLogo ? ((int) event.getRawX() - floatMenu.getWidth() / 2) : (int) event.getRawX() - floatMenu.getWidth();
                wmParams.y = centerInLogo ? (int) event.getRawY() - floatMenu.getWidth() / 2 : (int) event.getRawY() - floatMenu.getWidth();
                wManager.updateViewLayout(logoLay, wmParams);
                updataViewTime = 0;
            }
            ismove = true;
            rotateLogo();

        }
    }

    /**
     * up事件
     */

    private void eventUp(MotionEvent event) {
        if (rotateLogo) {
            hasAnimation = false;
            floatMenu.clearAnimation();
        }
        if (event.getRawX() > mScreenWidth / 2) {
            wmParams.x = mScreenWidth;
            defPositionShow = SHOW_RIGHT;
        } else {
            wmParams.x = 0;
            defPositionShow = SHOW_LEFT;
        }
        if (Math.abs((int) event.getRawX() - oldX) < 10 && Math.abs((int) event.getRawY() - oldY) < 10) {
            showMenu();
        } else {
            if (!showMenu) {
                if (!CommonUtils.isFullScreen(mActivity)) {
                    if (wmParams.y < CommonUtils.getStatusHeight(mActivity)) {
                        wmParams.y = CommonUtils.getStatusHeight(mActivity);
                    }
                }
                wManager.updateViewLayout(logoLay, wmParams);
                ismove = false;
                countDownTimer.cancel();
                countDownTimer.start();
            }
        }
    }

    public void rotateLogo() {
        if (rotateLogo) {
            if (!hasAnimation) {
                floatMenu.startAnimation(rotateAnimation);
                hasAnimation = true;
            }
        }
    }

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
        if (!showMenu) {
            if (wmParams.x == mScreenWidth) {
                if (onMenuClickListener != null) {
                    onMenuClickListener.open(SHOW_RIGHT);
                }
                fFloatMenuView.showLogoLeft();
                defPositionShow = SHOW_RIGHT;
            } else {
                if (onMenuClickListener != null) {
                    onMenuClickListener.open(SHOW_LEFT);
                }
                fFloatMenuView.showLogoRight();
                defPositionShow = SHOW_LEFT;
            }
            wManager.removeViewImmediate(logoLay);
            wManager.addView(fFloatMenuView, wmParams);
            showMenu = true;
            ismove = true;
            countDownTimer.cancel();
        }
    }

    /**
     * 隐藏悬浮窗菜单栏
     */

    public void hideMenu() {
        if (showMenu) {
            wManager.removeViewImmediate(fFloatMenuView);
            wManager.addView(logoLay, wmParams);
            showMenu = false;
            ismove = false;
            countDownTimer.cancel();
            countDownTimer.start();

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
            timeCancel();
            wManager.addView(logoLay, wmParams);
            countDownTimer.start();
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
            countDownTimer.cancel();
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

        void open(int status);
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
