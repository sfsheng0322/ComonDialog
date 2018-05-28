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

import com.sunfusheng.dialog.R;
import com.sunfusheng.dialog.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class PopupMenuWindow extends PopupWindow {

    protected Context context;
    protected LayoutInflater inflater;

    protected List<PopupMenuItemConfig> items = new ArrayList<>();
    protected int itemsCount;
    protected boolean showMore;

    public static int THRESHOLD = 4;
    public static int MARGIN;
    private static int screenWidth;
    private static int screenHeight;
    private static int itemWidth;
    private static int itemHeight;

    public PopupMenuWindow(Context context) {
        super(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(true);

        MARGIN = Utils.dp2px(context, 16);
        screenWidth = Utils.getScreenWidth(context);
        screenHeight = Utils.getScreenHeight(context);
        itemWidth = Utils.dp2px(context, 56);
        itemHeight = Utils.dp2px(context, 40);
    }

    public int getContentWidth() {
        if (showMore) {
            return THRESHOLD * itemWidth + getContentView().getMeasuredWidth();
        }
        return itemsCount * itemWidth;
    }

    public int getContentHeight() {
        return getContentView().getMeasuredHeight();
    }

    public int getMaxContentHeight() {
        int rowCount = itemsCount / THRESHOLD + (itemsCount % THRESHOLD == 0 ? 0 : 1);
        if (showMore) {
            return (rowCount - 1) * itemHeight + getContentHeight();
        }
        return getContentHeight();
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
        int maxContentHeight = getMaxContentHeight();
        Point offset = getOffset(frame, point, x, y, anchorWidth, anchorHeight, contentWidth, contentHeight, maxContentHeight);

        Log.d("--->", "=============================================");
        Log.d("--->", "screenWidth:" + screenWidth + " screenHeight:" + screenHeight);
        Log.d("--->", "frame:" + frame.toString());
        Log.d("--->", "point:" + point.toString());
        Log.d("--->", "x:" + x + " y:" + y);
        Log.d("--->", "anchorWidth:" + anchorWidth + " anchorHeight:" + anchorHeight);
        Log.d("--->", "contentWidth:" + contentWidth + " contentHeight:" + contentHeight + " maxContentHeight:" + maxContentHeight);
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
            if (screenWidth - frame.right < MARGIN) {
                frame.right = screenWidth - MARGIN;
            }
            if (screenHeight - frame.bottom < MARGIN) {
                frame.bottom = screenHeight - MARGIN;
            }
        }
        return location;
    }

    private Point getOffset(Rect frame, Point point, int x, int y, int anchorWidth, int anchorHeight,
                            int contentWidth, int contentHeight, int maxContentHeight) {
        Point offset = new Point();
        int offX;
        int offY;
        int pointX = point.x;
        int pointY = point.y;
        int topMargin = frame.top + pointY;
        boolean isLeft;

        if (screenWidth - pointX - contentWidth - MARGIN > 0) {
            offX = Math.max(pointX, MARGIN);
            isLeft = true;
        } else if (pointX - contentWidth - 2 * MARGIN > 0) {
            offX = pointX - contentWidth - MARGIN;
            isLeft = false;
        } else {
            isLeft = true;
            offX = (screenWidth - contentWidth) / 2;
        }

        if (topMargin > screenHeight / 2 || screenHeight - topMargin - contentHeight - 2 * MARGIN < 0) {
            offY = topMargin - y - anchorHeight - contentHeight - MARGIN;
            if (isLeft) {
                setAnimationStyle(R.style.animPopupMenuBottomLeft);
            } else {
                setAnimationStyle(R.style.animPopupMenuBottomRight);
            }
        } else {
            offY = topMargin - y - anchorHeight;
            if (isLeft) {
                setAnimationStyle(R.style.animPopupMenuTopLeft);
            } else {
                setAnimationStyle(R.style.animPopupMenuTopRight);
            }
        }

        offset.x = offX;
        offset.y = offY;
        return offset;
    }

}
