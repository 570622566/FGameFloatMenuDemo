package cn.hotapk.fgamefloatmenu.bean;

import android.support.annotation.DrawableRes;

/**
 * @author laijian
 * @version 2017/12/18
 * @Copyright (C)下午4:51 , www.hotapk.cn
 */
public class MenuItem {


    private int resid;
    private String title = "";

    public MenuItem() {

    }

    public MenuItem(int resid, String title) {
        this.resid = resid;
        this.title = title;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(@DrawableRes int resid) {
        this.resid = resid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
