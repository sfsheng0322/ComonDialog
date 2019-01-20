package com.sunfusheng.dialog.popup;

import android.content.Context;
import android.view.View;

public class CommonPopup extends BasePopup<CommonPopup> {

    private OnViewListener mOnViewListener;

    public static CommonPopup create() {
        return new CommonPopup();
    }

    public static CommonPopup create(Context context) {
        return new CommonPopup(context);
    }

    public CommonPopup() {

    }

    public CommonPopup(Context context) {
        setContext(context);
    }

    @Override
    protected void initAttributes() {

    }

    @Override
    protected void initViews(View view, CommonPopup popup) {
        if (mOnViewListener != null) {
            mOnViewListener.initViews(view, popup);
        }
    }

    public CommonPopup setOnViewListener(OnViewListener listener) {
        this.mOnViewListener = listener;
        return this;
    }

    public interface OnViewListener {

        void initViews(View view, CommonPopup popup);
    }
}
