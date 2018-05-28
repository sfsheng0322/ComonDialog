package com.sunfusheng.dialog.popupmenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunfusheng.dialog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng on 2018/5/25.
 */
public class PopupMenuAdapter extends RecyclerView.Adapter<PopupMenuAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<PopupMenuItemConfig> items;

    public PopupMenuAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public PopupMenuAdapter(Context context, List<PopupMenuItemConfig> items) {
        this.layoutInflater = LayoutInflater.from(context);
        this.items = items;
    }

    public void setItems(List<PopupMenuItemConfig> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_popup_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopupMenuItemConfig item = items.get(position);

        holder.vTitle.setText(item.titleResId);
        holder.vIcon.setImageResource(item.iconResId);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.itemView, item, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(holder.itemView, item, position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView vTitle;
        ImageView vIcon;

        ViewHolder(View itemView) {
            super(itemView);
            vTitle = itemView.findViewById(R.id.title);
            vIcon = itemView.findViewById(R.id.icon);
        }
    }

    private onItemClickListener onItemClickListener;
    private onItemLongClickListener onItemLongClickListener;

    public interface onItemClickListener {
        void onItemClick(View view, PopupMenuItemConfig item, int position);
    }

    public interface onItemLongClickListener {
        void onItemLongClick(View view, PopupMenuItemConfig item, int position);
    }

    public void setOnItemClickListener(PopupMenuAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(PopupMenuAdapter.onItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
