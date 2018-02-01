package com.sunfusheng.dialog.group_recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng on 2018/2/1.
 */
abstract public class GroupRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "GroupAdapter";

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CHILD = 1;
    public static final int TYPE_FOOTER = 2;

    private Context context;
    private LayoutInflater inflater;
    private List<List<T>> items;
    private int itemPosition;
    private int itemType;

    public GroupRecyclerViewAdapter(Context context) {
        this(context, new ArrayList<List<T>>());
    }

    public GroupRecyclerViewAdapter(Context context, List<List<T>> items) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    public List<List<T>> getItems() {
        return items;
    }

    public void setItems(List<List<T>> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(getLayoutId(), parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemType(position);
        int groupPosition = getGroupPosition(position);
        int childPosition = getGroupChildPosition(groupPosition, position);

        T item = items.get(groupPosition).get(childPosition);
        GroupViewHolder viewHolder = (GroupViewHolder) holder;

        if (TYPE_HEADER == itemType) {
            onBindHeaderViewHolder(viewHolder, item, groupPosition);
        } else if (TYPE_CHILD == itemType) {

            onBindChildViewHolder(viewHolder, item, groupPosition, childPosition);
        } else if (TYPE_FOOTER == itemType) {
            onBindFooterViewHolder(viewHolder, item, groupPosition);
        }
    }

    @Override
    public int getItemViewType(int position) {
        this.itemPosition = position;
        this.itemType = getItemType(position);
        int groupPosition = getGroupPosition(position);
        int childPosition = getGroupChildPosition(groupPosition, position);
        Log.d("--->", "# position: " + position + " groupPosition: " + groupPosition + " childPosition: " + childPosition);

        if (TYPE_HEADER == itemType) {
            return getHeaderItemType(groupPosition);
        } else if (TYPE_CHILD == itemType) {

            return getChildItemType(groupPosition, childPosition);
        } else if (TYPE_FOOTER == itemType) {
            return getFooterItemType(groupPosition);
        }
        return super.getItemViewType(position);
    }

    public int getItemType(int itemPosition) {
        int itemCount = 0;
        for (int i = 0, groupCount = getGroupCount(); i < groupCount; i++) {
            if (hasHeader()) {
                itemCount += 1;
                if (itemPosition < itemCount) {
                    return TYPE_HEADER;
                }
            }

            itemCount += getGroupItemCount(i);
            if (itemPosition < itemCount) {
                return TYPE_CHILD;
            }

            if (hasFooter()) {
                itemCount += 1;
                if (itemPosition < itemCount) {
                    return TYPE_FOOTER;
                }
            }
        }
        throw new RuntimeException("Determine item type failed, please check the data.");
    }

    public int getLayoutId() {
        int itemType = getItemType(getItemPosition());
        if (TYPE_HEADER == itemType) {
            return getHeaderLayoutId();
        } else if (TYPE_CHILD == itemType) {
            return getChildLayoutId();
        } else if (TYPE_FOOTER == itemType) {
            return getFooterLayoutId();
        }
        return 0;
    }

    /**
     * @return 返回列表下标
     */
    public int getItemPosition() {
        return itemPosition;
    }

    /**
     * @param itemPosition 列表下标
     * @return 返回所在组
     */
    public int getGroupPosition(int itemPosition) {
        int itemCount = 0;
        for (int i = 0, groupCount = getGroupCount(); i < groupCount; i++) {
            itemCount += getGroupItemCount(i);
            if (itemPosition < itemCount) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param groupPosition 组下标
     * @param itemPosition  列表下标
     * @return 返回所在组的下标
     */
    public int getGroupChildPosition(int groupPosition, int itemPosition) {
        if (groupPosition < getGroupCount()) {
            int itemCount = getGroupItemCountRange(0, groupPosition + 1);
            int position = getGroupItemCount(groupPosition) - (itemCount - itemPosition) - (hasFooter() ? 1 : 0);
            if (0 <= position) {
                return position;
            }
        }
        return -1;
    }

    public int getHeaderItemType(int groupPosition) {
        return TYPE_HEADER;
    }

    public int getChildItemType(int groupPosition, int childPosition) {
        return TYPE_CHILD;
    }

    public int getFooterItemType(int groupPosition) {
        return TYPE_FOOTER;
    }

    /**
     * @return 返回所有组所有项的个数
     */
    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (int i = 0, groupCount = getGroupCount(); i < groupCount; i++) {
            itemCount += itemCount + getGroupItemCount(i);
        }
        return itemCount;
    }

    /**
     * @return 返回所有组的个数
     */
    public int getGroupCount() {
        return null == items ? 0 : items.size();
    }

    /**
     * @param groupPosition 组下标
     * @return 返回指定组所有项的个数，包括header、child、footer
     */
    public int getGroupItemCount(int groupPosition) {
        if (0 == getGroupCount()) {
            return 0;
        }

        return null == items.get(groupPosition) ? 0 : items.get(groupPosition).size();
    }

    /**
     * @param groupPosition 组下标
     * @return 返回指定组所有child项的个数，只含有child，不包括header、footer
     */
    public int getGroupChildItemCount(int groupPosition) {
        int groupItemCount = getGroupItemCount(groupPosition);
        if (0 == groupItemCount) {
            return 0;
        }

        int childCount = groupItemCount - (hasHeader() ? 1 : 0) - (hasFooter() ? 1 : 0);
        if (0 > childCount) {
            return 0;
        }
        return childCount;
    }

    /**
     * @param start 开始的组
     * @param count 组的个数
     * @return 返回多个组的所有项
     */
    public int getGroupItemCountRange(int start, int count) {
        int itemCount = 0;
        for (int i = start, groupCount = getGroupCount(); i < start + count && i < groupCount; i++) {
            itemCount += getGroupItemCount(i);
        }
        return itemCount;
    }

    public boolean hasHeader() {
        return false;
    }

    public boolean hasFooter() {
        return false;
    }

    abstract public int getHeaderLayoutId();

    abstract public int getChildLayoutId();

    abstract public int getFooterLayoutId();

    abstract public void onBindHeaderViewHolder(GroupViewHolder holder, T item, int groupPosition);

    abstract public void onBindChildViewHolder(GroupViewHolder holder, T item, int groupPosition, int childPosition);

    abstract public void onBindFooterViewHolder(GroupViewHolder holder, T item, int groupPosition);

}
