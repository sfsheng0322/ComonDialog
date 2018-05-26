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
import com.sunfusheng.dialog.popupmenu.PopupMenu;
import com.sunfusheng.dialog.popupmenu.PopupMenuGestureDetector;
import com.sunfusheng.dialog.popupmenu.PopupMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class PopupMenuActivity extends AppCompatActivity {

    private PopupMenuGestureDetector popupMenuGestureDetector;
    private PopupMenu popupMenu;
    private List<PopupMenuItem> items = new ArrayList<>();
    private PopupMenuItem moreItem = new PopupMenuItem("更多", R.mipmap.ic_popup_menu_more);

    private List<PopupMenuItem> getItems() {
        List<PopupMenuItem> items = new ArrayList<>();
        items.add(new PopupMenuItem("转发", R.mipmap.ic_popup_menu_share));
        items.add(new PopupMenuItem("回复", R.mipmap.ic_popup_menu_reply));
        items.add(new PopupMenuItem("复制", R.mipmap.ic_popup_menu_copy));
        items.add(new PopupMenuItem("收藏", R.mipmap.ic_popup_menu_favorite));
        items.add(new PopupMenuItem("扬声器播放", R.mipmap.ic_popup_menu_speaker));
        items.add(new PopupMenuItem("听筒播放", R.mipmap.ic_popup_menu_earphone));
        items.add(new PopupMenuItem("删除", R.mipmap.ic_popup_menu_delete));
        items.add(new PopupMenuItem("静音播放", R.mipmap.ic_popup_menu_mute));
        items.add(new PopupMenuItem("撤回", R.mipmap.ic_popup_menu_undo));
        items.add(new PopupMenuItem("强制撤回", R.mipmap.ic_popup_menu_force_undo));
        items.add(new PopupMenuItem("多选", R.mipmap.ic_popup_menu_multi_select));
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

        popupMenuGestureDetector = new PopupMenuGestureDetector(this, recyclerView);
    }

    private int getPosition(GroupRecyclerViewAdapter adapter, int groupPosition, int childPosition) {
        int position = 0;
        for (int i = 0; i < groupPosition; i++) {
            position += GroupAdapterUtils.countGroupItems(adapter.getGroups(), groupPosition);
        }
        position += childPosition;
        return position;
    }

    private void showPopupMenu(View anchorView, List<PopupMenuItem> items) {
        popupMenu = new PopupMenu(this, popupMenuGestureDetector, items);
        popupMenu.show(anchorView);
    }

}
