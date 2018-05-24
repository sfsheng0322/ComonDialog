package com.sunfusheng.dialog.popupmenu;

import android.support.annotation.DrawableRes;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class PopupMenuItem {

    public String title;
    @DrawableRes
    public int icon;

    public PopupMenuItem(String title, @DrawableRes int icon) {
        this.title = title;
        this.icon = icon;
    }
}
