package cn.hotapk.fgamefloatmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

import cn.hotapk.fgamefloatmenu.bean.MenuItem;

/**
 * @author laijian
 * @version 2017/12/18
 * @Copyright (C)下午11:52 , www.hotapk.cn
 */
public class FFloatMenuBuilder {
    private Activity mActivity;
    private int bgRadius = 100;//背景圆角
    private int bgColor = Color.TRANSPARENT;//背景颜色
    private int logoWdith = 46;//logo宽度 dp
    private int logoRes;//logo图片资源
    private int marginLogoLeft = 6;//menu的布局到logo左边距离
    private int marginLogoRight = 12;//menu的布局到右边距离
    private int itemIconSize = 30;//item的icon大小
    private int itemTextSize = 10;//item的字体大小
    private int itemMarginLeft = 4;//item间距
    private int itemMarginRight = 4;
    private int itemMarginTop = 2;
    private int itemMarginBottom = 2;
    private int textMarginTop = 1;
    private int defPositionShow = 1;//初始化默认显示在左上角 1 ： 左上角 2 ：右上角
    private int defOffsetY = 70;//初始化默认Y坐标的偏移量
    private float viewAlpha = 1f;//靠边缩小时透明度，默认不透明
    private boolean rotateLogo = false;//拖拽悬浮窗时是否启用旋转动画
    private boolean centerInLogo = true;//拖拽点是否在悬浮球中心点
    private int millisInFuture = 6;//半隐藏悬浮球倒计时 秒
    private int hideLogoSize = 0;//半隐藏logo靠边移动大小 dp
    private boolean oneShow = false;//半隐藏时是否点击就显示菜单栏

    private List<MenuItem> menuItems = new ArrayList<>();//item

    public FFloatMenuBuilder(Activity activity) {
        this.mActivity = activity;
    }

    public FFloatMenuBuilder setBgRadius(int bgRadius) {
        this.bgRadius = bgRadius;
        return this;
    }

    public FFloatMenuBuilder setBgColor(@ColorRes int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public FFloatMenuBuilder setLogoWdith(int logoWdith) {
        this.logoWdith = logoWdith;
        return this;
    }


    public FFloatMenuBuilder setLogoRes(@DrawableRes int logoRes) {
        this.logoRes = logoRes;
        return this;
    }

    public FFloatMenuBuilder setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        return this;
    }

    public FFloatMenuBuilder addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
        return this;
    }

    public FFloatMenuBuilder setMarginLogoLeft(int marginLogoLeft) {
        this.marginLogoLeft = marginLogoLeft;
        return this;
    }

    public FFloatMenuBuilder setMarginLogoRight(int marginLogoRight) {
        this.marginLogoRight = marginLogoRight;
        return this;
    }

    public FFloatMenuBuilder setItemIconSize(int itemIconSize) {
        this.itemIconSize = itemIconSize;
        return this;
    }

    public FFloatMenuBuilder setItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
        return this;
    }

    public FFloatMenuBuilder setItemMarginLeft(int itemMarginLeft) {
        this.itemMarginLeft = itemMarginLeft;
        return this;
    }

    public FFloatMenuBuilder setItemMarginRight(int itemMarginRight) {
        this.itemMarginRight = itemMarginRight;
        return this;
    }

    public FFloatMenuBuilder setItemMarginTop(int itemMarginTop) {
        this.itemMarginTop = itemMarginTop;
        return this;
    }

    public FFloatMenuBuilder setItemMarginBottom(int itemMarginBottom) {
        this.itemMarginBottom = itemMarginBottom;
        return this;
    }

    public FFloatMenuBuilder setTextMarginTop(int textMarginTop) {
        this.textMarginTop = textMarginTop;
        return this;
    }

    public FFloatMenuBuilder setDefPositionShow(int defPositionShow) {
        this.defPositionShow = defPositionShow;
        return this;
    }

    public FFloatMenuBuilder setDefOffsetY(int defOffsetY) {
        this.defOffsetY = defOffsetY;
        return this;
    }

    public FFloatMenuBuilder setViewAlpha(float viewAlpha) {
        this.viewAlpha = viewAlpha;
        return this;

    }

    public FFloatMenuBuilder setRotateLogo(boolean rotateLogo) {
        this.rotateLogo = rotateLogo;
        return this;
    }

    public FFloatMenuBuilder setCenterInLogo(boolean centerInLogo) {
        this.centerInLogo = centerInLogo;
        return this;
    }

    public FFloatMenuBuilder setMillisInFuture(int millisInFuture) {
        this.millisInFuture = millisInFuture;
        return this;
    }

    public FFloatMenuBuilder setHideLogoSize(int hideLogoSize) {
        this.hideLogoSize = hideLogoSize;
        return this;
    }

    public FFloatMenuBuilder setOneShow(boolean oneShow) {
        this.oneShow = oneShow;
        return this;
    }

    public Activity getmActivity() {
        return mActivity;
    }

    public int getBgRadius() {
        return bgRadius;
    }

    public int getBgColor() {
        return bgColor;
    }

    public int getLogoWdith() {
        return logoWdith;
    }

    public int getLogoRes() {
        return logoRes;
    }

    public int getMarginLogoLeft() {
        return marginLogoLeft;
    }

    public int getMarginLogoRight() {
        return marginLogoRight;
    }

    public int getItemIconSize() {
        return itemIconSize;
    }

    public int getItemTextSize() {
        return itemTextSize;
    }

    public int getItemMarginLeft() {
        return itemMarginLeft;
    }

    public int getItemMarginRight() {
        return itemMarginRight;
    }

    public int getItemMarginTop() {
        return itemMarginTop;
    }

    public int getItemMarginBottom() {
        return itemMarginBottom;
    }

    public int getTextMarginTop() {
        return textMarginTop;
    }

    public int getDefPositionShow() {
        return defPositionShow;
    }

    public int getDefOffsetY() {
        return defOffsetY;
    }

    public float getViewAlpha() {
        return viewAlpha;
    }

    public boolean isRotateLogo() {
        return rotateLogo;
    }

    public boolean isCenterInLogo() {
        return centerInLogo;
    }

    public int getMillisInFuture() {
        return millisInFuture;
    }

    public int getHideLogoSize() {
        return hideLogoSize;
    }

    public boolean isOneShow() {
        return oneShow;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public FFloatMenu build() {
        FFloatMenu fFloatMenu = new FFloatMenu(this);
        return fFloatMenu;
    }

}
