package com.sunfusheng.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.sunfusheng.GroupAdapterUtils;
import com.sunfusheng.GroupRecyclerViewAdapter;
import com.sunfusheng.dialog.adapter.StringGroupAdapter;
import com.sunfusheng.dialog.datasource.DataSource;
import com.sunfusheng.dialog.popupmenu.IPopupMenuGestureDetector;
import com.sunfusheng.dialog.popupmenu.PopupMenu;
import com.sunfusheng.dialog.popupmenu.PopupMenuGestureDetector;
import com.sunfusheng.dialog.popupmenu.PopupMenuItemConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class PopupMenuActivity extends AppCompatActivity {

    private IPopupMenuGestureDetector gestureDetector;
    private PopupMenu popupMenu;
    private List<PopupMenuItemConfig> items = new ArrayList<>();
    private PopupMenuItemConfig moreItem = new PopupMenuItemConfig(R.string.item_menu_more, R.mipmap.ic_popup_menu_more);

    private List<PopupMenuItemConfig> getItems() {
        List<PopupMenuItemConfig> items = new ArrayList<>();
        items.add(new PopupMenuItemConfig(R.string.item_menu_share, R.mipmap.ic_popup_menu_share));
        items.add(new PopupMenuItemConfig(R.string.item_menu_reply, R.mipmap.ic_popup_menu_reply));
        items.add(new PopupMenuItemConfig(R.string.item_menu_copy, R.mipmap.ic_popup_menu_copy));
        items.add(new PopupMenuItemConfig(R.string.item_menu_favorite, R.mipmap.ic_popup_menu_favorite));
        items.add(new PopupMenuItemConfig(R.string.item_menu_speaker, R.mipmap.ic_popup_menu_speaker));
        items.add(new PopupMenuItemConfig(R.string.item_menu_earphone, R.mipmap.ic_popup_menu_earphone));
        items.add(new PopupMenuItemConfig(R.string.item_menu_delete, R.mipmap.ic_popup_menu_delete));
        items.add(new PopupMenuItemConfig(R.string.item_menu_mute, R.mipmap.ic_popup_menu_mute));
        items.add(new PopupMenuItemConfig(R.string.item_menu_undo, R.mipmap.ic_popup_menu_undo));
        items.add(new PopupMenuItemConfig(R.string.item_menu_force_undo, R.mipmap.ic_popup_menu_force_undo));
        items.add(new PopupMenuItemConfig(R.string.item_menu_multi_select, R.mipmap.ic_popup_menu_multi_select));
        return items;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        items = getItems();
    }

    private void initView() {
        setTitle(R.string.popup_menu);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
//        linearLayout.setOnLongClickListener(v -> {
//            showPopupMenu(linearLayout, items);
//            return true;
//        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        StringGroupAdapter groupAdapter = new StringGroupAdapter(this, DataSource.popupMenuItems);
        recyclerView.setAdapter(groupAdapter);

        groupAdapter.setOnItemLongClickListener((adapter, data, groupPosition, childPosition) -> {
            if (null != data) {
                int position = getPosition(adapter, groupPosition, childPosition);
                View itemView = linearLayoutManager.findViewByPosition(position);
                showPopupMenu(itemView, items);
            }
        });

        gestureDetector = new PopupMenuGestureDetector(recyclerView);
    }

    private int getPosition(GroupRecyclerViewAdapter adapter, int groupPosition, int childPosition) {
        int position = 0;
        for (int i = 0; i < groupPosition; i++) {
            position += GroupAdapterUtils.countGroupItems(adapter.getGroups(), groupPosition);
        }
        position += childPosition;
        return position;
    }

    private void showPopupMenu(View anchorView, List<PopupMenuItemConfig> items) {
        popupMenu = new PopupMenu(this, items);
        popupMenu.show(anchorView, gestureDetector);
    }

}
