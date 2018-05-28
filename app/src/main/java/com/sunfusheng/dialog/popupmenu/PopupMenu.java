package com.sunfusheng.dialog.popupmenu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sunfusheng.dialog.R;

import java.util.ArrayList;
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

    private PopupMenuGestureDetector popupMenuGestureDetector;
    private PopupMenuAdapter adapter;

    public PopupMenu(Context context, PopupMenuGestureDetector popupMenuGestureDetector, List<PopupMenuItem> items) {
        super(context);
        this.popupMenuGestureDetector = popupMenuGestureDetector;
        vView = inflater.inflate(R.layout.layout_popup_menu, null);
        setContentView(vView);
        vView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        vRecyclerView = vView.findViewById(R.id.recyclerView);
        adapter = new PopupMenuAdapter(context, items);
        vRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view, item, position) -> {
            Toast.makeText(view.getContext(), item.title, Toast.LENGTH_SHORT).show();
            dismiss();
        });
        vMore = vView.findViewById(R.id.ll_more);
        vDivider = vView.findViewById(R.id.divider);
        vClose = vView.findViewById(R.id.close);

        vMore.setOnClickListener(v -> {
            showMore = false;
            vMore.setVisibility(View.GONE);
            vClose.setVisibility(View.VISIBLE);
            adapter.setItems(handlePopupMenuItems(showMore));
        });
        vClose.setOnClickListener(v -> dismiss());
        setItems(items);
    }

    public void setItems(List<PopupMenuItem> items) {
        if (items == null || items.size() == 0) {
            return;
        }

        this.items = items;
        this.itemsCount = items.size();
        this.showMore = itemsCount > THRESHOLD;
        vMore.setVisibility(showMore ? View.VISIBLE : View.GONE);
        vDivider.setVisibility(itemsCount > THRESHOLD ? View.VISIBLE : View.GONE);
        vClose.setVisibility(View.GONE);

        vRecyclerView.setLayoutManager(new GridLayoutManager(context, getSpanCount()));
        adapter.setItems(handlePopupMenuItems(showMore));
    }

    private int getSpanCount() {
        return showMore ? THRESHOLD : itemsCount;
    }

    private List<PopupMenuItem> handlePopupMenuItems(boolean showMore) {
        if (showMore) {
            List<PopupMenuItem> result = new ArrayList<>();
            for (int i = 0; i < THRESHOLD; i++) {
                result.add(items.get(i));
            }
            return result;
        }
        return items;
    }

    public void show(View anchorView) {
        if (popupMenuGestureDetector == null) {
            throw new RuntimeException("popupMenuGestureDetector is null, please init first.");
        }

        Rect frameRect = new Rect();
        popupMenuGestureDetector.getFrameView().getGlobalVisibleRect(frameRect);
        Point touchPoint = popupMenuGestureDetector.getTouchPoint();
        show(anchorView, frameRect, touchPoint);
    }
}
