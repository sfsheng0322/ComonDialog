package com.sunfusheng.dialog.popupmenu;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author sunfusheng on 2018/5/26.
 */
public class PopupMenuGestureDetector implements IPopupMenuGestureDetector {

    private View frameView;
    private Point touchPoint;
    private GestureDetector gestureDetector;

    public PopupMenuGestureDetector() {
    }

    public PopupMenuGestureDetector(View frameView) {
        setOnTouchListener(frameView);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnTouchListener(View frameView) {
        if (frameView == null) return;
        this.frameView = frameView;
        gestureDetector = new GestureDetector(frameView.getContext(), simpleOnGestureListener);
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

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    @Override
    public View getFrameView() {
        return frameView;
    }

    @Override
    public Point getTouchPoint() {
        return touchPoint;
    }
}
