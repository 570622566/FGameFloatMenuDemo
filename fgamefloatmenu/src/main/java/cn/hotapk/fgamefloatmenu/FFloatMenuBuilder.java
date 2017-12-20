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
    private int bgRadius = 100;
    private int bgColor = Color.TRANSPARENT;
    private int logoWdith = 46;
    private int logoRes;
    private int marginLogoLeft = 6;
    private int marginLogoRight = 12;
    private int itemIconSize = 30;
    private int itemTextSize = 10;
    private int itemMarginLeft = 4;
    private int itemMarginRight = 4;
    private int itemMarginTop = 2;
    private int itemMarginBottom = 2;
    private int textMarginTop = 1;
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

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public FFloatMenu build() {
        FFloatMenu fFloatMenu = new FFloatMenu(this);
        return fFloatMenu;
    }

}
