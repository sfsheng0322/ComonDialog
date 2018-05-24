package com.sunfusheng.dialog.adapter;

import android.content.Context;

import com.sunfusheng.GroupViewHolder;
import com.sunfusheng.HeaderGroupRecyclerViewAdapter;
import com.sunfusheng.dialog.R;
import com.sunfusheng.dialog.datasource.DataSource;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class MainGroupAdapter extends HeaderGroupRecyclerViewAdapter<com.sunfusheng.dialog.datasource.DataSource.MainItemConfig> {

    public MainGroupAdapter(Context context, DataSource.MainItemConfig[][] items) {
        super(context, items);
    }

    @Override
    public int getHeaderLayoutId(int viewType) {
        return R.layout.divider_20dp;
    }

    @Override
    public int getChildLayoutId(int viewType) {
        return R.layout.item_main;
    }

    @Override
    public void onBindHeaderViewHolder(GroupViewHolder holder, DataSource.MainItemConfig item, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(GroupViewHolder holder, DataSource.MainItemConfig item, int groupPosition, int childPosition) {
        holder.setText(R.id.tv_title, item.titleId);
        holder.setVisible(R.id.divider, !isGroupLastItem(groupPosition, childPosition));
    }
}
