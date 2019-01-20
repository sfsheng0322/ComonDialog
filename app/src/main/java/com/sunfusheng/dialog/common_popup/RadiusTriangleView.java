package com.sunfusheng.dialog.common_popup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.sunfusheng.dialog.R;
import com.sunfusheng.dialog.util.Utils;

/**
 * @author sunfusheng on 2019/1/20.
 */
public class RadiusTriangleView extends View {
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 6;
    private static final int DEFAULT_COLOR = R.color.colorPrimary;

    private Paint mPaint;
    private int mColor;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int mDirection;
    private Path mPath;

    public RadiusTriangleView(Context context) {
        this(context, null);
    }

    public RadiusTriangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadiusTriangleView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RadiusTriangleView, 0, 0);
        mDirection = typedArray.getInt(R.styleable.RadiusTriangleView_rtv_direction, TOP);
        mColor = typedArray.getColor(R.styleable.RadiusTriangleView_rtv_color, ContextCompat.getColor(getContext(), DEFAULT_COLOR));
        mRadius = (int) typedArray.getDimension(R.styleable.RadiusTriangleView_rtv_radius, 0);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mWidth == 0 || MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
            mWidth = Utils.dp2px(getContext(), DEFAULT_WIDTH);
        }
        if (mHeight == 0 || MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            mHeight = Utils.dp2px(getContext(), DEFAULT_HEIGHT);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w2 = mWidth * 1f / 2;
        float h2 = mHeight * 1f / 2;

        switch (mDirection) {
            case TOP: {
                mPath.moveTo(0, mHeight);
                mPath.lineTo(mWidth, mHeight);
                float d = (float) (mRadius * w2 / Math.sqrt(Math.pow(w2, 2) + Math.pow(mHeight, 2)));
                float x1 = w2 + d;
                float y1 = (float) Math.sqrt(Math.pow(mRadius, 2) - Math.pow(d, 2));
                float x2 = w2 - d;
                mPath.lineTo(x1, y1);
                if (mRadius > 0) {
                    mPath.quadTo(w2, 0, x2, y1);
                }
            }
            break;
            case BOTTOM: {
                mPath.moveTo(0, 0);
                float d = (float) (mRadius * w2 / Math.sqrt(Math.pow(w2, 2) + Math.pow(mHeight, 2)));
                float x1 = w2 - d;
                float y1 = mHeight - (float) Math.sqrt(Math.pow(mRadius, 2) - Math.pow(d, 2));
                float x2 = w2 + d;
                mPath.lineTo(x1, y1);
                if (mRadius > 0) {
                    mPath.quadTo(w2, mHeight, x2, y1);
                }
                mPath.lineTo(mWidth, 0);
            }
            break;
            case LEFT: {
                float d = (float) (mRadius * h2 / Math.sqrt(Math.pow(h2, 2) + Math.pow(mWidth, 2)));
                float x1 = (float) Math.sqrt(Math.pow(mRadius, 2) - Math.pow(d, 2));
                float y1 = h2 - d;
                float y2 = h2 + d;
                mPath.moveTo(x1, y1);
                if (mRadius > 0) {
                    mPath.quadTo(0, h2, x1, y2);
                }
                mPath.lineTo(mWidth, mHeight);
                mPath.lineTo(mWidth, 0);
            }
            break;
            case RIGHT: {
                mPath.moveTo(0, 0);
                mPath.lineTo(0, mHeight);
                float d = (float) (mRadius * h2 / Math.sqrt(Math.pow(h2, 2) + Math.pow(mWidth, 2)));
                float x1 = mWidth - (float) Math.sqrt(Math.pow(mRadius, 2) - Math.pow(d, 2));
                float y1 = h2 + d;
                float y2 = h2 - d;
                mPath.lineTo(x1, y1);
                if (mRadius > 0) {
                    mPath.quadTo(mWidth, h2, x1, y2);
                }
            }
            break;
        }
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

}
