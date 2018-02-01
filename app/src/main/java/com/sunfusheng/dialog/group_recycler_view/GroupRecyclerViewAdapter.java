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

    private OnHeaderClickListener onHeaderClickListener;
    private OnChildClickListener onChildClickListener;
    private OnFooterClickListener onFooterClickListener;

    public GroupRecyclerViewAdapter(Context context) {
        this(context, new ArrayList<List<T>>());
    }

    public GroupRecyclerViewAdapter(Context context, List<List<T>> items) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = checkData(items);
    }

    private List<List<T>> checkData(List<List<T>> data) {
        List<List<T>> items = new ArrayList<>();
        if (null != data && 0 < data.size()) {
            for (int i = 0; i < data.size(); i++) {
                if (null != data.get(i) && 0 < data.get(i).size()) {
                    items.add(data.get(i));
                }
            }
        }
        return items;
    }

    public List<List<T>> getItems() {
        return items;
    }

    public void setItems(List<List<T>> items) {
        this.items = checkData(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(getLayoutId(), parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = confirmItemViewType(position);
        int groupPosition = getGroupPosition(position);
        int childPosition = getGroupChildPosition(groupPosition, position);
        Log.d("--->", "position: " + position + " groupPosition: " + groupPosition + " childPosition: " + childPosition);

        T item = items.get(groupPosition).get(childPosition);
        GroupViewHolder viewHolder = (GroupViewHolder) holder;

        if (TYPE_HEADER == itemType) {
            if (null != onHeaderClickListener) {
                holder.itemView.setOnClickListener(view -> {
                    if (null != onHeaderClickListener) {
                        onHeaderClickListener.onHeaderClick(this, viewHolder, groupPosition);
                    }
                });
            }
            onBindHeaderViewHolder(viewHolder, item, groupPosition);
        } else if (TYPE_CHILD == itemType) {
            if (null != onChildClickListener) {
                holder.itemView.setOnClickListener(view -> {
                    if (null != onChildClickListener) {
                        onChildClickListener.onChildClick(this, viewHolder, groupPosition, childPosition);
                    }
                });
            }
            onBindChildViewHolder(viewHolder, item, groupPosition, childPosition);
        } else if (TYPE_FOOTER == itemType) {
            if (null != onFooterClickListener) {
                holder.itemView.setOnClickListener(view -> {
                    if (null != onFooterClickListener) {
                        onFooterClickListener.onFooterClick(this, viewHolder, groupPosition);
                    }
                });
            }
            onBindFooterViewHolder(viewHolder, item, groupPosition);
        }
    }

    @Override
    public int getItemViewType(int position) {
        this.itemPosition = position;
        this.itemType = confirmItemViewType(position);
        int groupPosition = getGroupPosition(position);

        if (TYPE_HEADER == itemType) {
            return getHeaderItemType(groupPosition);
        } else if (TYPE_CHILD == itemType) {
            int childPosition = getGroupChildPosition(groupPosition, position);
            return getChildItemType(groupPosition, childPosition);
        } else if (TYPE_FOOTER == itemType) {
            return getFooterItemType(groupPosition);
        }
        return super.getItemViewType(position);
    }

    public int confirmItemViewType(int itemPosition) {
        int itemCount = 0;
        for (int i = 0, groupCount = getGroupCount(); i < groupCount; i++) {
            if (hasHeader()) {
                itemCount += 1;
                if (itemPosition < itemCount) {
                    return TYPE_HEADER;
                }
            }

            itemCount += getGroupChildCount(i);
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
        throw new IndexOutOfBoundsException("Confirm item type failed, " + "itemPosition = " + itemPosition + ", itemCount = " + itemCount);
    }

    public int getLayoutId() {
        int itemType = confirmItemViewType(getItemPosition());
        if (TYPE_HEADER == itemType) {
            return getHeaderLayoutId();
        } else if (TYPE_CHILD == itemType) {
            return getChildLayoutId();
        } else if (TYPE_FOOTER == itemType) {
            return getFooterLayoutId();
        }
        return 0;
    }

    public T getHeaderItem(int groupPosition) {
        return items.get(groupPosition).get(0);
    }

    public T getChildItem(int groupPosition, int childPosition) {
        return items.get(groupPosition).get(childPosition);
    }

    public T getFooterItem(int groupPosition) {
        return items.get(groupPosition).get(getGroupItemCount(groupPosition) - 1);
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
     * @return 返回指定组header的列表下标，如果没有header返回-1
     */
    public int getGroupHeaderPosition(int groupPosition) {
        if (groupPosition < getGroupCount()) {
            if (!hasHeader()) {
                return -1;
            }
            return getGroupItemCountRange(0, groupPosition);
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
            int position = itemPosition - getGroupItemCountRange(0, groupPosition);
            if (0 <= position) {
                return position;
            }
        }
        return -1;
    }

    /**
     * @param groupPosition 组下标
     * @return 返回指定组footer的列表下标，如果没有footer返回-1
     */
    public int getGroupFooterPosition(int groupPosition) {
        if (groupPosition < getGroupCount()) {
            if (!hasFooter()) {
                return -1;
            }
            return getGroupItemCountRange(0, groupPosition + 1) - 1;
        }
        return -1;
    }

    /**
     * @return 返回所有组所有项的个数
     */
    @Override
    public int getItemCount() {
        return getGroupItemCountRange(0, getGroupCount());
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
    public int getGroupChildCount(int groupPosition) {
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

    public int getHeaderItemType(int groupPosition) {
        return TYPE_HEADER;
    }

    public int getChildItemType(int groupPosition, int childPosition) {
        return TYPE_CHILD;
    }

    public int getFooterItemType(int groupPosition) {
        return TYPE_FOOTER;
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

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public void setOnFooterClickListener(OnFooterClickListener onFooterClickListener) {
        this.onFooterClickListener = onFooterClickListener;
    }

    public interface OnHeaderClickListener {
        void onHeaderClick(GroupRecyclerViewAdapter adapter, GroupViewHolder holder, int groupPosition);
    }

    public interface OnChildClickListener {
        void onChildClick(GroupRecyclerViewAdapter adapter, GroupViewHolder holder, int groupPosition, int childPosition);
    }

    public interface OnFooterClickListener {
        void onFooterClick(GroupRecyclerViewAdapter adapter, GroupViewHolder holder, int groupPosition);
    }

}
