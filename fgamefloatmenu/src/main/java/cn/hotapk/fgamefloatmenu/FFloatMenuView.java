package cn.hotapk.fgamefloatmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hotapk.fgamefloatmenu.bean.MenuItem;

/**
 * @author laijian
 * @version 2017/12/15
 * @Copyright (C)下午4:18 , www.hotapk.cn
 */
public class FFloatMenuView extends LinearLayout {
    /**
     * 显示logo
     */
    private ImageView logo;

    /**
     * 显示菜单栏
     */
    private LinearLayout menuLay;

    /**
     * 菜单栏布局
     */
    private LinearLayout.LayoutParams menuLayParams;

    /**
     * 菜单item数据
     */
    private List<MenuItem> menuItems = new ArrayList<>();//item

    /**
     * 菜单item布局
     */
    private List<LinearLayout> linearLayouts = new ArrayList<>();

    /**
     * 点击事件
     */
    private OnItemClickListener onItemClickListener;
    /**
     * 背景圆角
     */
    private int bgRadius;

    /**
     * 背景颜色
     */
    private int bgColor;

    /**
     * logo宽高
     */
    private int logoWdith;

    /**
     * logo图片资源
     */
    private int logoRes;

    /**
     * 菜单栏在logo左边距离
     */
    private int marginLogoLeft;

    /**
     * 菜单栏在logo右边距离
     */
    private int marginLogoRight;

    /**
     * item图标的宽高
     */
    private int itemIconSize;

    /**
     * item文字大小
     */
    private int itemTextSize;

    /**
     * item间距
     */
    private int itemMarginLeft;
    private int itemMarginRight;
    private int itemMarginTop;
    private int itemMarginBottom;

    /**
     * item文字top距离
     */
    private int textMarginTop;


    public FFloatMenuView(Context context) {
        this(context, null);
    }

    public FFloatMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FFloatMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        menuLayParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setGravity(Gravity.CENTER_VERTICAL);
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(layoutParams);
        logo = new ImageView(getContext());
        menuLay = new LinearLayout(getContext());
        menuLay.setGravity(Gravity.CENTER_VERTICAL);
        menuLay.setOrientation(HORIZONTAL);
        logo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.dismiss();
                }
            }
        });

    }

    /**
     * 显示在logo右边
     */
    public void showMenuRight() {
        updataItemView(true);
        menuLayParams.setMarginStart(dip2px(marginLogoLeft));
        menuLayParams.setMarginEnd(dip2px(marginLogoRight));
        menuLay.setLayoutParams(menuLayParams);

    }


    /**
     * 显示在logo左边
     */
    public void showMenuLeft() {
        updataItemView(false);
        menuLayParams.setMarginStart(dip2px(marginLogoRight));
        menuLayParams.setMarginEnd(dip2px(marginLogoLeft));
        menuLay.setLayoutParams(menuLayParams);
    }

    /**
     * 更新菜单栏item摆放位置
     *
     * @param showLeft
     */
    private void updataItemView(boolean showLeft) {
        menuLay.removeAllViews();
        removeView(menuLay);
        if (showLeft) {
            for (int i = 0; i < linearLayouts.size(); i++) {
                menuLay.addView(linearLayouts.get(i));
            }
            addView(menuLay);
        } else {
            for (int i = linearLayouts.size() - 1; i >= 0; i--) {
                menuLay.addView(linearLayouts.get(i));
            }
            addView(menuLay, 0);
        }
    }

    /**
     * 更新布局
     */
    public void updataView() {
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setCornerRadius(bgRadius);
        gd.setColor(ContextCompat.getColor(getContext(), bgColor));//添加背景颜色
        setBackground(gd);
        LinearLayout.LayoutParams logoLayParams = new LayoutParams(dip2px(logoWdith), dip2px(logoWdith));
        logo.setLayoutParams(logoLayParams);
        logo.setBackgroundResource(logoRes);
        addView(logo);
        initmenuItems();
    }

    /**
     * 初始化item布局
     */
    private void initmenuItems() {
        for (int i = 0; i < menuItems.size(); i++) {
            final int position = i;
            /**
             * item线性布局
             * 垂直方向
             * 居中对其
             */
            LinearLayout menuItemLay = new LinearLayout(getContext());
            LinearLayout.LayoutParams itemLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemLayoutParams.setMargins(dip2px(itemMarginLeft), dip2px(itemMarginTop), dip2px(itemMarginRight), dip2px(itemMarginBottom));
            menuItemLay.setGravity(Gravity.CENTER);
            menuItemLay.setOrientation(VERTICAL);
            menuItemLay.setLayoutParams(itemLayoutParams);

            /**
             * 嵌套ImageView
             */
            ImageView itemImg = new ImageView(getContext());
            LinearLayout.LayoutParams itemImgParams = new LayoutParams(dip2px(itemIconSize), dip2px(itemIconSize));
            itemImg.setBackgroundResource(menuItems.get(i).getResid());
            itemImgParams.gravity = Gravity.CENTER_HORIZONTAL;
            itemImg.setLayoutParams(itemImgParams);

            /**
             * 嵌套TextView
             */
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams itemTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemTextParams.setMargins(0, dip2px(textMarginTop), 0, 0);
            itemTextParams.gravity = Gravity.CENTER_HORIZONTAL;
            textView.setText(menuItems.get(i).getTitle());
            textView.setTextSize(itemTextSize);
            textView.setTextColor(Color.WHITE);
            textView.setLayoutParams(itemTextParams);
            menuItemLay.addView(itemImg);
            menuItemLay.addView(textView);
            menuItemLay.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            linearLayouts.add(menuItemLay);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void dismiss();
    }

    public void clearViews() {
        menuLay.removeAllViews();
        linearLayouts.clear();
        removeAllViews();
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setBgRadius(int bgRadius) {
        this.bgRadius = bgRadius;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setLogoWdith(int logoWdith) {
        this.logoWdith = logoWdith;
    }

    public void setLogoRes(int logoRes) {
        this.logoRes = logoRes;
    }

    public void setMarginLogoLeft(int marginLogoLeft) {
        this.marginLogoLeft = marginLogoLeft;
    }

    public void setMarginLogoRight(int marginLogoRight) {
        this.marginLogoRight = marginLogoRight;
    }

    public void setItemIconSize(int itemIconSize) {
        this.itemIconSize = itemIconSize;
    }

    public void setItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
    }

    public void setItemMarginLeft(int itemMarginLeft) {
        this.itemMarginLeft = itemMarginLeft;
    }

    public void setItemMarginRight(int itemMarginRight) {
        this.itemMarginRight = itemMarginRight;
    }

    public void setItemMarginTop(int itemMarginTop) {
        this.itemMarginTop = itemMarginTop;
    }

    public void setItemMarginBottom(int itemMarginBottom) {
        this.itemMarginBottom = itemMarginBottom;
    }

    public void setTextMarginTop(int textMarginTop) {
        this.textMarginTop = textMarginTop;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public int dip2px(float dipValue) {
        final float scale = getContext().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static final class Builder {

        private FFloatMenuBuilder fFloatMenuBuilder;
        private OnItemClickListener onItemClickListener;

        public Builder() {
        }

        public Builder setBuilder(FFloatMenuBuilder fFloatMenuBuilder) {
            this.fFloatMenuBuilder = fFloatMenuBuilder;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public FFloatMenuView creat() {
            FFloatMenuView fFloatMenuView = new FFloatMenuView(fFloatMenuBuilder.getmActivity());
            fFloatMenuView.setBgRadius(fFloatMenuBuilder.getBgRadius());//设置圆角
            fFloatMenuView.setBgColor(fFloatMenuBuilder.getBgColor());//设置背景
            fFloatMenuView.setLogoWdith(fFloatMenuBuilder.getLogoWdith());//设置logo宽高
            fFloatMenuView.setLogoRes(fFloatMenuBuilder.getLogoRes());//设置图片资源
            fFloatMenuView.setMenuItems(fFloatMenuBuilder.getMenuItems());//设置item数据
            fFloatMenuView.setOnItemClickListener(onItemClickListener);//设置事件监听
            fFloatMenuView.setMarginLogoLeft(fFloatMenuBuilder.getMarginLogoLeft());//菜单栏和logo左边距离
            fFloatMenuView.setMarginLogoRight(fFloatMenuBuilder.getMarginLogoRight());//菜单栏和logo右边距离
            fFloatMenuView.setItemIconSize(fFloatMenuBuilder.getItemIconSize());//菜单栏item图片宽高
            fFloatMenuView.setItemTextSize(fFloatMenuBuilder.getItemTextSize());//菜单栏item字体大小
            fFloatMenuView.setItemMarginLeft(fFloatMenuBuilder.getItemMarginLeft());//菜单栏item左间距
            fFloatMenuView.setItemMarginRight(fFloatMenuBuilder.getItemMarginRight());
            fFloatMenuView.setItemMarginTop(fFloatMenuBuilder.getItemMarginTop());
            fFloatMenuView.setItemMarginBottom(fFloatMenuBuilder.getItemMarginBottom());
            fFloatMenuView.setTextMarginTop(fFloatMenuBuilder.getTextMarginTop());//菜单栏item文字top间隔
            fFloatMenuView.updataView();
            return fFloatMenuView;
        }
    }

}
