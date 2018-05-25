package com.sunfusheng.dialog.popupmenu;

import android.content.Context;
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

    private static int THRESHOLD = 4;

    private PopupMenuAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private List<PopupMenuItem> items = new ArrayList<>();
    private PopupMenuItem moreItem;
    private int itemsCount;
    private boolean showMore;

    public PopupMenu(Context context) {
        this(context, null, new ArrayList<>());
    }

    public PopupMenu(Context context, View frameView, List<PopupMenuItem> items) {
        super(context);
        vView = inflater.inflate(R.layout.layout_popup_menu, null);
        setContentView(vView);
        setFrameView(frameView);

        vRecyclerView = vView.findViewById(R.id.recyclerView);
        adapter = new PopupMenuAdapter(context, items);
        vRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view, item, position) -> {
            Toast.makeText(view.getContext(), item.title, Toast.LENGTH_SHORT).show();
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

    public void setThreshold(int threshold) {
        PopupMenu.THRESHOLD = threshold;
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

        gridLayoutManager = new GridLayoutManager(context, getSpanCount());
        vRecyclerView.setLayoutManager(gridLayoutManager);
        adapter.setItems(handlePopupMenuItems(showMore));
    }

    public void setMoreItem(PopupMenuItem moreItem) {
        this.moreItem = moreItem;
    }

    private int getSpanCount() {
        return showMore ? THRESHOLD : itemsCount;
    }

    private List<PopupMenuItem> handlePopupMenuItems(boolean showMore) {
        if (showMore && moreItem != null) {
            List<PopupMenuItem> result = new ArrayList<>();
            for (int i = 0; i < THRESHOLD; i++) {
                result.add(items.get(i));
            }
            return result;
        }
        return items;
    }
}
