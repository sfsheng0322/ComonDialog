package com.sunfusheng.dialog.popupmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author sunfusheng on 2018/5/26.
 */
@SuppressLint("ClickableViewAccessibility")
public class PopupMenuGestureDetector {

    private Context context;
    private View frameView;
    private Point touchPoint;
    private GestureDetector gestureDetector;

    public PopupMenuGestureDetector(Context context) {
        this.context = context;
    }

    public PopupMenuGestureDetector(Context context, View frameView) {
        this.context = context;
        setOnTouchListener(frameView);
    }

    public void setOnTouchListener(View frameView) {
        if (frameView == null) return;
        this.frameView = frameView;
        gestureDetector = new GestureDetector(context, simpleOnGestureListener);
        if (frameView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) frameView;
            recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    return gestureDetector.onTouchEvent(e);
                }
            });
        } else {
            frameView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
        }
    }

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            touchPoint = new Point((int) e.getX(), (int) e.getY());
            return super.onDown(e);
        }
    };

    public View getFrameView() {
        return frameView;
    }

    public Point getTouchPoint() {
        return touchPoint;
    }
}
