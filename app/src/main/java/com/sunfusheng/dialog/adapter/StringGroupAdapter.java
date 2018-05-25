package com.sunfusheng.dialog.adapter;

import android.content.Context;

import com.sunfusheng.GroupViewHolder;
import com.sunfusheng.HeaderGroupRecyclerViewAdapter;
import com.sunfusheng.dialog.R;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class StringGroupAdapter extends HeaderGroupRecyclerViewAdapter<String> {

    public StringGroupAdapter(Context context, String[][] items) {
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
    public void onBindHeaderViewHolder(GroupViewHolder holder, String item, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(GroupViewHolder holder, String item, int groupPosition, int childPosition) {
        holder.setText(R.id.tv_title, item);
        holder.setTextColor(R.id.tv_title, context.getResources().getColor(R.color.font2));
        holder.setVisible(R.id.divider, !isGroupLastItem(groupPosition, childPosition));
    }
}
