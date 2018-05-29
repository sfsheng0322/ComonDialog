package com.sunfusheng.dialog.popupmenu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sunfusheng.dialog.R;
import com.sunfusheng.dialog.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class PopupMenu extends PopupMenuWindow {

    private View vView;
    private RecyclerView vRecyclerView;
    private LinearLayout vMore;
    private View vDivider;
    private ImageView vClose;

    private PopupMenuAdapter adapter;

    private onMenuItemClickListener onMenuItemClickListener;
    private onMenuMoreClickListener onMenuMoreClickListener;
    private OnMenuCloseClickListener onMenuCloseClickListener;

    public PopupMenu(Context context, PopupMenuItemConfig[] items) {
        this(context, Arrays.asList(items));
    }

    public PopupMenu(Context context, List<PopupMenuItemConfig> items) {
        super(context);
        vView = inflater.inflate(R.layout.layout_popup_menu, null);
        setContentView(vView);
        vView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        vRecyclerView = vView.findViewById(R.id.recyclerView);
        vMore = vView.findViewById(R.id.ll_more);
        vDivider = vView.findViewById(R.id.divider);
        vClose = vView.findViewById(R.id.close);

        adapter = new PopupMenuAdapter(context, items);
        vRecyclerView.setAdapter(adapter);
        setItems(items);

        adapter.setOnItemClickListener((view, item, position) -> {
            if (onMenuItemClickListener != null) {
                onMenuItemClickListener.onMenuItemClick(view, item, position);
            }
            dismiss();
        });

        vMore.setOnClickListener(v -> {
            vMore.setVisibility(View.GONE);
            vClose.setVisibility(View.VISIBLE);
            adapter.setItems(handlePopupMenuItems(false));
            if (onMenuMoreClickListener != null) {
                onMenuMoreClickListener.onMenuMore(this);
            }
        });

        vClose.setOnClickListener(v -> {
            dismiss();
            if (onMenuCloseClickListener != null) {
                onMenuCloseClickListener.onMenuClose(this);
            }
        });
    }

    public void setItems(PopupMenuItemConfig[] items) {
        if (Utils.isEmpty(items)) return;
        setItems(Arrays.asList(items));
    }

    public void setItems(List<PopupMenuItemConfig> items) {
        if (Utils.isEmpty(items)) return;
        this.items = items;
        this.itemsCount = items.size();
        this.showMore = itemsCount > THRESHOLD;
        vMore.setVisibility(showMore ? View.VISIBLE : View.GONE);
        vDivider.setVisibility(itemsCount > THRESHOLD ? View.VISIBLE : View.GONE);
        vClose.setVisibility(View.GONE);

        vRecyclerView.setLayoutManager(new GridLayoutManager(context, showMore ? THRESHOLD : itemsCount));
        adapter.setItems(handlePopupMenuItems(showMore));
    }

    private List<PopupMenuItemConfig> handlePopupMenuItems(boolean showMore) {
        if (showMore) {
            List<PopupMenuItemConfig> result = new ArrayList<>();
            for (int i = 0; i < THRESHOLD; i++) {
                result.add(items.get(i));
            }
            return result;
        }
        return items;
    }

    public void show(View anchorView, IPopupMenuGestureDetector gestureDetector) {
        Rect frameRect = new Rect();
        gestureDetector.getFrameView().getGlobalVisibleRect(frameRect);
        Point touchPoint = gestureDetector.getTouchPoint();
        show(anchorView, frameRect, touchPoint);
    }

    public void show(View anchorView, Rect frameView, Point touchPoint) {
        showPopupMenu(anchorView, frameView, touchPoint);
    }

    public interface onMenuItemClickListener {
        void onMenuItemClick(View view, PopupMenuItemConfig item, int position);
    }

    public interface onMenuMoreClickListener {
        void onMenuMore(PopupMenu popupMenu);
    }

    public interface OnMenuCloseClickListener {
        void onMenuClose(PopupMenu popupMenu);
    }

    public void setOnItemClickListener(onMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnMenuMoreClickListener(onMenuMoreClickListener onMenuMoreClickListener) {
        this.onMenuMoreClickListener = onMenuMoreClickListener;
    }

    public void setOnMenuCloseClickListener(OnMenuCloseClickListener onMenuCloseClickListener) {
        this.onMenuCloseClickListener = onMenuCloseClickListener;
    }
}
