package com.sunfusheng.dialog.popupmenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.sunfusheng.dialog.util.DisplayUtil;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class PopupMenuWindow extends PopupWindow {

    protected Context context;
    protected LayoutInflater inflater;

    private static int MARGIN;
    private int screenWidth;
    private int screenHeight;

    public PopupMenuWindow(Context context) {
        super(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(true);

        MARGIN = DisplayUtil.dp2px(context, 16);
        screenWidth = DisplayUtil.getScreenWidth(context);
        screenHeight = DisplayUtil.getScreenHeight(context);
    }

    public int getContentWidth() {
        return getContentView().getMeasuredWidth();
    }

    public int getContentHeight() {
        return getContentView().getMeasuredHeight();
    }

    public void show(View anchor, Rect frame, Point point) {
        if (frame == null) {
            frame = new Rect();
        }
        if (point == null) {
            point = new Point(-1, -1);
        }

        int[] location = reviseFrameAndPoint(anchor, frame, point);
        int x = location[0];
        int y = location[1];
        int anchorWidth = anchor.getWidth();
        int anchorHeight = anchor.getHeight();
        int contentWidth = getContentWidth();
        int contentHeight = getContentHeight();
        Point offset = getOffset(frame, point, x, y, anchorWidth, anchorHeight, contentWidth, contentHeight);

        Log.d("--->", "screenWidth:" + screenWidth + " screenHeight:" + screenHeight);
        Log.d("--->", "frame:" + frame.toString());
        Log.d("--->", "point:" + point.toString());
        Log.d("--->", "x:" + x + " y:" + y);
        Log.d("--->", "anchorWidth:" + anchorWidth + " anchorHeight:" + anchorHeight);
        Log.d("--->", "contentWidth:" + contentWidth + " contentHeight:" + contentHeight);
        Log.d("--->", "offset:" + offset.toString());

        showAsDropDown(anchor, offset.x, offset.y);
    }

    public int[] reviseFrameAndPoint(View anchor, Rect frame, Point point) {
        int[] location = new int[2];
        anchor.getLocationInWindow(location);

        if (point.x < 0 || point.y < 0) {
            point.set(anchor.getWidth() >> 1, anchor.getHeight() >> 1);
        }

        if (frame.isEmpty()) {
            anchor.getWindowVisibleDisplayFrame(frame);
        }

        if (!frame.isEmpty()) {
            frame.left = Math.max(MARGIN, frame.left);
            frame.top = Math.max(MARGIN, frame.top);
            if (DisplayUtil.getScreenWidth(context) - frame.right < MARGIN) {
                frame.right = DisplayUtil.getScreenWidth(context) - MARGIN;
            }
            if (DisplayUtil.getScreenHeight(context) - frame.bottom < MARGIN) {
                frame.bottom = DisplayUtil.getScreenHeight(context) - MARGIN;
            }
        }
        return location;
    }

    private Point getOffset(Rect frame, Point point, int x, int y, int anchorWidth, int anchorHeight, int contentWidth, int contentHeight) {
        Point offset = new Point();
        int offX;
        int offY;
        int pointX = point.x;
        int pointY = point.y;
        int topMargin = frame.top + pointY;

        if (screenWidth - pointX - contentWidth - MARGIN > 0) {
            offX = Math.max(pointX, MARGIN);
        } else if (pointX - contentWidth - 2 * MARGIN > 0) {
            offX = pointX - contentWidth - MARGIN;
        } else {
            offX = (screenWidth - contentWidth) / 2;
        }

        if (topMargin > screenHeight / 3 || screenHeight - topMargin - contentHeight - 2 * MARGIN < 0) {
            offY = topMargin - y - anchorHeight - contentHeight - MARGIN;
        } else {
            offY = topMargin - y - anchorHeight;
        }

        offset.x = offX;
        offset.y = offY;
        return offset;
    }

}
