package com.sunfusheng.dialog.group_recycler_view;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author sunfusheng on 2018/2/1.
 */
abstract public class GroupItemView<T, VH extends GroupViewHolder> {

    abstract public VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    abstract public int getGroupCount();

    abstract public int getChildrenCount(int groupPosition);

    abstract public boolean hasHeader(int groupPosition);

    abstract public boolean hasFooter(int groupPosition);

    abstract public int getHeaderLayout(int viewType);

    abstract public int getFooterLayout(int viewType);

    abstract public int getChildLayout(int viewType);

    abstract public void onBindHeaderViewHolder(VH holder, int groupPosition);

    abstract public void onBindFooterViewHolder(VH holder, int groupPosition);

    abstract public void onBindChildViewHolder(VH holder, int groupPosition, int childPosition);
}
