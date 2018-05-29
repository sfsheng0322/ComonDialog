package com.sunfusheng.dialog.popupmenu;

import android.graphics.Point;
import android.view.View;

/**
 * @author sunfusheng on 2018/5/29.
 */
public interface IPopupMenuGestureDetector {
    View getFrameView();
    Point getTouchPoint();
}
